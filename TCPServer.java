// TCPServer2.java: Multithreaded server
import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TCPServer{
    ArrayList<Integer> Autenticados = new ArrayList<>();
    public static void main(String args[]){
        int numero=0;
        try{
            AdminRMIimplements rmi = (AdminRMIimplements) LocateRegistry.getRegistry(6789).lookup("HelloRMI");
            rmi.sayHello();
            int serverPort = Integer.parseInt(args[0]);
            System.out.println("A Escuta no Porto "+serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET="+listenSocket);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                numero ++;
                new Connection(clientSocket, numero);
		System.out.println(numero);
            }
        }catch(IOException e)
        {System.out.println("Listen:" + e.getMessage());
        }catch (NotBoundException e){
            System.out.println(e.getMessage());
        }
    }
}
//= Thread para tratar de cada canal de comunicacao com um cliente
class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    int thread_number;
    public Connection (Socket aClientSocket, int numero) {
        thread_number = numero;
        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
    }
    //=============================
	public static int verifica (String data, DataOutputStream out){
        data=data.toUpperCase();
        if (!data.contains(";"))
            return 0;
        String[] nova = data.split(";");
		ArrayList<String> lista= new ArrayList<>();
		for (String pal: nova){
            String[] temp=pal.split("\\|");
			lista.add(temp[0]);
			lista.add(temp[1]);
        }
        System.out.println(lista);        
        String type;
        if ( lista.get(0).equals("TYPE")){
            try{
            type=lista.get(1);
            if (type.equals("READY") && lista.size()==4){
                out.writeUTF("type|unlock;user|iduser");
                return 1;
            }
            else if(type.equals("AUTH") && lista.size()==6){
                out.writeUTF("verificar se o eleitor existe ou não\ntype|auth;result|ok\ntype|boletim;......");
                return 1;
            }
            else if(type.equals("VOTE") && lista.size()==4){
                out.writeUTF("EXIT");
                return 2;
            }
            }catch(IOException e){System.out.println("IO:" + e);}
        }
        else 
            return 0;
        return 0;
    }

    public void run(){
    
        String resposta;
        try{
            while(true){
                //an echo server
                String data = in.readUTF();
				data=data.toUpperCase();
                System.out.println("CLIENT ["+thread_number+"]");
				if (verifica(data,out)==0)
                    out.writeUTF("SIGA O EXEMPLO");
                else if (verifica(data,out)==2){
                    in.close();
                    out.close();
                    clientSocket.shutdownInput();
                    clientSocket.shutdownOutput();
                    clientSocket.close();
                }
            }
        }catch(EOFException e){System.out.println("EOF:" + e);
        }catch(IOException e){System.out.println("IO:" + e);}
    }
}


