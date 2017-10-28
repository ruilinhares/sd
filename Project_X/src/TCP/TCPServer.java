package TCP;// TCPServer2.java: Multithreaded server
import Classes.*;
import RMI.TCPserverRMIimplements;

import java.net.*;
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

import static TCP.TCPServer.reconectarRMI;
import static java.lang.System.exit;

public class TCPServer implements Serializable{
    private static final long serialVersionUID = 1L;
    private Departamento departamento;
    private ArrayList<Eleicao> listaEleicoes;
    private Boolean estadoMesa;

    public TCPServer(Departamento departamento) {
        this.departamento = departamento;
        this.listaEleicoes = new ArrayList<>();
        this.estadoMesa = false;
    }

    public void setEstadoMesa(Boolean estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public void addEleicao(Eleicao e) {
        this.listaEleicoes.add(e);
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public ArrayList<Eleicao> getListaEleicoes() {
        return listaEleicoes;
    }

    public Boolean getEstadoMesa() {
        return estadoMesa;
    }

    static TCPserverRMIimplements reconectarRMI()   {
        TCPserverRMIimplements rmi;
        int sleep = 1000;
        while(true) {
            try {
                rmi = (TCPserverRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI");
                rmi.sayHello();
                return rmi;
            } catch (NotBoundException | RemoteException | MalformedURLException ignored) {
                System.out.println("putinha");
                try {
                    Thread.sleep(sleep);
                    sleep *= 2;
                    if (sleep > 16000) {
                        System.out.println("\n\t*Avaria no RMI Server*");
                        exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]){
        int numero=0;
        try{
            ServerSocket listenSocket;
            TCPserverRMIimplements rmi = (TCPserverRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI"); // ligar ao rmi
            Departamento dep;
            Scanner scanner = new Scanner(System.in);
            if(args.length == 2)
                listenSocket = new ServerSocket(Integer.parseInt(args[0]),50,InetAddress.getByName(args[1]));
            else {

                System.out.print("PORT: ");
                int port = scanner.nextInt();
                System.out.print("IP: ");
                String ip = scanner.next();
                listenSocket = new ServerSocket(port, 50,InetAddress.getByName(ip));
            }
            System.out.print("Departamento: ");
            String nomeDep = scanner.next();
            try{
                dep = rmi.abrirMesaVoto(nomeDep);
            }catch (RemoteException re){
                rmi = reconectarRMI();
                dep = rmi.abrirMesaVoto(nomeDep);
            }
            if (dep==null){ //abrir mesa de voto retorna departamento da mesa
                System.out.println("\t*Erro*\nNao mesa inexistente ou fechada");
                listenSocket.close();
                exit(0);
            }
            System.out.println("LISTEN SOCKET="+listenSocket);
            while (true) {
                System.out.println("[1]Inserir elietor para votar\n[0]Fechar mesa de voto");
                System.out.print("->");
                String escolha = scanner.next();
                switch (escolha) {
                    // por esta mesa de voto OFF(estado da mesa = false)
                    case "0":
                        try{
                            rmi.fecharMesaVoto(dep.getNome());
                        }catch (RemoteException re){
                            rmi = reconectarRMI();
                            rmi.fecharMesaVoto(dep.getNome());
                        }
                        System.out.println("\n\t*Mesa de Voto fechada*\n");
                        exit(0);
                        break;

                    // identificar eleitor e eleicao
                    case "1":
                        System.out.print("Numero de CC do novo eleitor: ");
                        Pessoa eleitor;
                        String read = scanner.next();
                        try{
                            eleitor = rmi.identificarEleitor(read); // ver se o eleitor existe, se sim, retorna a pessoa
                        }catch (RemoteException e){ // fail over
                            rmi = reconectarRMI();
                            eleitor = rmi.identificarEleitor(read);
                        }
                        if (eleitor == null) { // eleitor nao identificado
                            System.out.println("\n\t*Eleitor não identificado!*\n");
                        } else { // eleitor identificado
                            System.out.println("\t*Eleitor identificado!*\n\nLista de Eleicoes disponives para votar:");
                            ArrayList<Eleicao> eleicoes;
                            try{
                                eleicoes = rmi.identificarEleicoes(eleitor, dep); // eleicoes disponiveis para o leitor votar
                            }catch (RemoteException e){ // fail over
                                rmi = reconectarRMI();
                                eleicoes = rmi.identificarEleicoes(eleitor, dep);
                            }
                            if (!eleicoes.isEmpty()) {
                                int i = 0;
                                for (Eleicao aux : eleicoes)
                                    System.out.println("[" + (++i) + "]" + aux.getTitulo());
                                System.out.print("->");
                                int opcao = scanner.nextInt();
                                Eleicao eleicao;
                                try{
                                    eleicao = rmi.escolherEleicao(eleitor, dep, opcao);
                                }catch (RemoteException e){ // fail over
                                    rmi = reconectarRMI();
                                    eleicao = rmi.escolherEleicao(eleitor, dep, opcao);
                                }
                                if (eleicao != null) { // eleicao identificada pelo o eleitor
                                    Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                                    System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                                    numero++;
                                    new Connection(clientSocket, rmi, eleicao, dep, eleitor);
                                    System.out.println("CLIENT ["+numero+"]");
                                } else // eleicao nao identificada
                                    System.out.println("\n\t*Opcao invalida*\n");
                            }else
                                System.out.println("\n\t*Sem eleicoes disponives para votar*\n");
                        }
                        break;
                    default:
                        System.out.println("\n\t*Opcao invalida*\n");
                        break;
                }
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
    private Eleicao eleicao; //identificador de eleicao
    private Departamento departamento; //local da eleicao
    private Pessoa eleitor; //eleitor
    private ArrayList<ListaCandidata> votosLista; //lista dos candidatos
    private TCPserverRMIimplements rmi;

    Connection(Socket aClientSocket, TCPserverRMIimplements rmi, Eleicao eleicao, Departamento departamento, Pessoa eleitor) {
        this.eleicao = eleicao;
        this.departamento = departamento;
        this.rmi = rmi;
        this.eleitor = eleitor;
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
            lista.add(temp[0]);
            lista.add(temp[1]);
        }
        return lista;
    }

    private void closeSocket() throws IOException {
        this.in.close();
        this.out.close();
        this.clientSocket.shutdownInput();
        this.clientSocket.shutdownOutput();
        this.clientSocket.close();
        exit(0);
    }

    public void run(){
        try {
            ArrayList<String> verifica;
            // TYPE/UNLOCK identificar eleitor com o nº CC
            this.out.writeUTF("\tPasso 1: Autenticar com o seu numero da UC e a sua password (preencher nos '*')" +
                    "\t'type|auth;uc|*numero da uc*;password|*password*'");

            //----- TYPE/AUTH autenticar eleitor -----
            clientSocket.setSoTimeout(120000);
            if ((verifica = verifica(this.in)) != null && verifica.get(0).equals("TYPE") && verifica.get(1).equals("AUTH") &&
                    verifica.get(2).equals("UC") && verifica.get(4).equals("PASSWORD") && verifica.size() == 6) {

                if (!(this.eleitor.getPassword().toUpperCase().equals(verifica.get(5)))) {
                    this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
                    closeSocket();
                }
                StringBuilder booletim = new StringBuilder("");
                int i = 0;
                try {
                    this.votosLista = rmi.getListaCandidatas(this.eleicao, this.eleitor); // listas candidatas
                } catch (RemoteException e) { // fail over
                    rmi = reconectarRMI();
                    this.votosLista = rmi.getListaCandidatas(this.eleicao, this.eleitor);
                }

                booletim.append("\tPasso 3: Votar numa das listas do boletim (preencher nos '*')" + "\t'type|vote;option|*numero da lista*'\nTYPE|BULLETIN");
                if (this.votosLista != null) {
                    for (ListaCandidata aux : this.votosLista)
                        booletim.append(";").append(aux.getNome()).append("|").append(++i);
                }
                this.out.writeUTF(booletim.toString());

                //----- TYPE/VOTE voto do eleitor -----
                clientSocket.setSoTimeout(120000);
                if ((verifica = verifica(this.in)) != null && verifica.get(0).equals("TYPE") &&
                        verifica.get(1).equals("VOTE") && verifica.get(2).equals("OPTION") && verifica.size() == 4) {
                    i = Integer.parseInt(verifica.get(3)) - 1;

                    Voto voto = new Voto(this.eleitor, this.votosLista.get(i), this.departamento);
                    try {
                        rmi.votacaoEleitor(this.eleicao, voto);
                    } catch (RemoteException e) { // fail over
                        rmi = reconectarRMI();
                        rmi.votacaoEleitor(this.eleicao, voto);
                    }
                    this.out.writeUTF("TYPE|VOTE;VOTE|CONFIRMED");
                } else
                    this.out.writeUTF("TYPE|VOTE;OPTION|UNACKNOWLEDGED");
            } else
                this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
            try{
                this.out.writeUTF("EXIT");
            }catch (Exception ignored){}
            closeSocket();
        }catch (SocketTimeoutException ig){
            try {
                this.out.writeUTF("TYPE|TIME;RESPONSE TIME|TIMEOUT");
                closeSocket();
            } catch (IOException ignored) {}
        }catch(EOFException e){System.out.println("EOF:" + e);
        }catch(IOException e){System.out.println();
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}
