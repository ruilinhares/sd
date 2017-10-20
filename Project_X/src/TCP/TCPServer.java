package TCP;// TCPServer2.java: Multithreaded server
import Classes.*;
import RMI.TCPserverRMIimplements;

import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.util.*;
import java.rmi.registry.LocateRegistry;

public class TCPServer{
    private final int eleicaoHashCode;
    private final Departamento departamento;

    public TCPServer(int eleicao, Departamento departamento) {
        this.eleicaoHashCode = eleicao;
        this.departamento = departamento;
    }

    //metodo para abrir mesas de voto

    public static void main(String args[]){
        int numero=0;
        ArrayList<Integer> Autenticados = new ArrayList<>();
        try{
            //RMIserver h = (RMIserver) Naming.lookup("rmi://localhost:7000");
            TCPserverRMIimplements rmi = (TCPserverRMIimplements) LocateRegistry.getRegistry(6789).lookup("HelloRMI");
            int serverPort = 6000;//Integer.parseInt(args[0]);
            System.out.println("A Escuta no Porto "+serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET="+listenSocket);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                numero ++;
                new Connection(clientSocket, rmi, numero, 0/*Integer.parseInt(args[1])*/, null/*Integer.parseInt(args[2])*/);
		    System.out.println(numero);
            }
        }catch(IOException e)
        {System.out.println("Listen:" + e.getMessage());} catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}

//= Thread para tratar de cada canal de comunicacao com um cliente
class Connection extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private int thread_number;
    private int eleicaoID; //identificador de eleicao
    private Departamento departamento; //local da eleicao
    private Pessoa eleitor; //eleitor
    private ArrayList<ListaCandidata> votosLista; //lista dos candidatos
    private TCPserverRMIimplements rmi;

    Connection(Socket aClientSocket, TCPserverRMIimplements rmi, int numero, int eleicao, Departamento departamento) {
        this.thread_number = numero;
        this.eleicaoID = eleicao;
        this.departamento = departamento;
        this.rmi = rmi;
        this.eleitor = null;
        this.votosLista = null;

        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
    }
    //=============================
	private static ArrayList<String> verifica(DataInputStream in) throws IOException {
        //an echo server
        String data = in.readUTF();
        data=data.toUpperCase();
        if (!data.contains(";"))
            return null;
        String[] nova = data.split(";");
		ArrayList<String> lista= new ArrayList<>();
		for (String pal: nova){
            String[] temp=pal.split("\\|");
            System.out.println(temp[1]);
            lista.add(temp[0]);
			lista.add(temp[1]);
        }
        System.out.println(lista);        
        return lista;
    }

    private void closeSocket() throws IOException {
        this.in.close();
        this.out.close();
        this.clientSocket.shutdownInput();
        this.clientSocket.shutdownOutput();
        this.clientSocket.close();
        System.exit(0);
    }

    public void run(){
        try{
            ArrayList<String> verifica;

            System.out.println("CLIENT ["+this.thread_number+"]");
            // TYPE/UNLOCK identificar eleitor com o nº CC
            if((verifica = verifica(this.in))!=null && verifica.get(0).equals("TYPE") &&
                     verifica.get(1).equals("UNLOCK") && verifica.get(2).equals("CC") && verifica.size()==4){
                this.eleitor = rmi.identificarEleitor(this.eleicaoID, verifica.get(3));
                if (this.eleitor==null){
                    this.out.writeUTF("TYPE|LOCK;CC|NOT FOUND");
                    closeSocket();
                }
                this.out.writeUTF("TYPE|UNLOCK;CC|FOUND\n\tPasso 2: Autenticar com o seu numero da UC e a sua password (preencher nos '*')" +
                        "\t'type|auth;uc|*numero da uc*;password|*password*'");

                //----- TYPE/AUTH autenticar eleitor -----
                if((verifica = verifica(this.in))!=null && verifica.get(0).equals("TYPE") && verifica.get(1).equals("AUTH") &&
                            verifica.get(2).equals("UC") && verifica.get(4).equals("PASSWORD") && verifica.size()==6){

                    if (this.eleitor==null || !(this.eleitor.getPassword().equals(verifica.get(5)))){
                        this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
                        closeSocket();
                    }
                    String booletim = "TYPE|BULLETIN";
                    int i = 1;
                    this.votosLista = rmi.getListaCandidatas(this.eleicaoID, this.eleitor);
                    if (this.votosLista != null)
                        for (ListaCandidata aux : this.votosLista){
                        booletim = ";"+i+"|"+aux.getNome();
                        i++;
                    }
                    booletim += "\n\tPasso 3: Votar numa das listas do boletim (preencher nos '*')" +
                            "\t'type|vote;option|*numero da lista*'";
                    this.out.writeUTF(booletim);

                    //----- TYPE/VOTE voto do eleitor -----
                    if ((verifica = verifica(this.in))!=null && verifica.get(0).equals("TYPE") &&
                            verifica.get(1).equals("VOTE") && verifica.get(3).equals("OPTION") && verifica.size()==4) {
                        i = Integer.parseInt(verifica.get(3)) - 1;

                        Voto voto = new Voto(this.eleitor, this.votosLista.get(i), this.departamento);
                        rmi.votacaoEleitor(this.eleicaoID, voto);
                        this.out.writeUTF("\nTYPE|VOTE;VOTE|CONFIRMED");
                    }else
                        this.out.writeUTF("TYPE|VOTE;OPTION|UNACKNOWLEDGED");
                }else
                    this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
            }else
                this.out.writeUTF("TYPE|ERROR");
            closeSocket();
        }catch(EOFException e){System.out.println("EOF:" + e);
        }catch(IOException e){System.out.println("IO:" + e);
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}


