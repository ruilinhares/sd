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
import static java.lang.System.setOut;

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

    static void reconectarRMI(TCPserverRMIimplements rmi){
        int sleep = 1000;
        Boolean flag = false;
        while(!flag){
            try {
                flag = true;
                Thread.sleep(sleep);
                rmi = (TCPserverRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI");
                System.out.println(rmi);
                rmi.sayHello();
            } catch (NotBoundException | RemoteException | MalformedURLException ignored) {
            } catch (InterruptedException e1) {
                flag = false;
                System.out.println("putinha");
                sleep*=2;
                if (sleep>16000) {
                    System.out.println("\n\t*Avaria no RMI Server*");
                    exit(0);
                }
            }
        }
    }
    static void reconectarteste(TCPserverRMIimplements rmi){
        Boolean flag = false;
        try {
            rmi.wait(30000);
            System.out.println("tou aqui");
            while (!flag) {
                System.out.println("merda merda");
                try {
                    rmi = (TCPserverRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI");
                    flag = true;
                    rmi.notify();
                } catch (NotBoundException | RemoteException | MalformedURLException ignored) {
                    flag = false;
                }
            }
        }catch (InterruptedException e) {
            System.out.println("merda");
        }
    }


    public static void main(String args[]){
        int numero=0;
        try{
            TCPserverRMIimplements rmi = (TCPserverRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI"); // ligar ao rmi
            Departamento dep;

            if ((dep = rmi.abrirMesaVoto("dei"/*args[1]*/))==null){ //abrir mesa de voto retorna departamento da mesa
                System.out.println("\t*Erro*\nNao mesa inexistente ou fechada");
                exit(0);
            }
            int serverPort = 6000;//Integer.parseInt(args[0]); // recerber o port
            System.out.println("A Escuta no Porto "+serverPort);
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET="+listenSocket);
            Scanner scanner = new Scanner(System.in);
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
                            System.out.println("merda");
                            reconectarRMI(rmi);
                            try {
                                rmi.fecharMesaVoto(dep.getNome());
                            }catch (Exception ignored){}
                        }
                        System.out.println("\n\t*Mesa de Voto fechada*\n");
                        exit(0);
                        break;

                    // identificar eleitor e eleicao
                    case "1":
                        System.out.print("Numero de CC do novo eleitor: ");
                        Pessoa eleitor = null;
                        String read = scanner.next();
                        try{
                            eleitor = rmi.identificarEleitor(read); // ver se o eleitor existe, se sim, retorna a pessoa
                        }catch (Exception e){ // fail over
                            reconectarRMI(rmi);
                            eleitor = rmi.identificarEleitor(read);
                        }
                        if (eleitor == null) { // eleitor nao identificado
                            System.out.println("\n\t*Eleitor não identificado!*\n");
                        } else { // eleitor identificado
                            System.out.println("\t*Eleitor identificado!*\n\nLista de Eleicoes disponives para votar:");
                            ArrayList<Eleicao> eleicoes = new ArrayList<>();
                            try{
                                eleicoes = rmi.identificarEleicoes(eleitor, dep); // eleicoes disponiveis para o leitor votar
                            }catch (Exception e){ // fail over
                                reconectarRMI(rmi);
                                eleicoes = rmi.identificarEleicoes(eleitor, dep);
                            }
                            if (!eleicoes.isEmpty()) {
                                int i = 0;
                                for (Eleicao aux : eleicoes)
                                    System.out.println("[" + (++i) + "]" + aux.getTitulo());
                                System.out.print("->");
                                int opcao = scanner.nextInt();
                                int eleicaoid = -1;
                                try{
                                    eleicaoid = rmi.escolherEleicao(eleitor, dep, opcao);
                                }catch (Exception e){ // fail over
                                    reconectarRMI(rmi);
                                    eleicaoid = rmi.escolherEleicao(eleitor, dep, opcao);
                                }
                                if (eleicaoid != -1) { // eleicao identificada pelo o eleitor
                                    Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                                    System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                                    numero++;
                                    new Connection(clientSocket, rmi, eleicaoid, dep, eleitor);
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
    private int eleicaoID; //identificador de eleicao (hashcode)
    private Departamento departamento; //local da eleicao
    private Pessoa eleitor; //eleitor
    private ArrayList<ListaCandidata> votosLista; //lista dos candidatos
    private TCPserverRMIimplements rmi;

    Connection(Socket aClientSocket, TCPserverRMIimplements rmi, int eleicao, Departamento departamento, Pessoa eleitor) {
        this.eleicaoID = eleicao;
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
        try{
            ArrayList<String> verifica;
            // TYPE/UNLOCK identificar eleitor com o nº CC
            this.out.writeUTF("\tPasso 1: Autenticar com o seu numero da UC e a sua password (preencher nos '*')" +
                    "\t'type|auth;uc|*numero da uc*;password|*password*'");

            //----- TYPE/AUTH autenticar eleitor -----
            if((verifica = verifica(this.in))!=null && verifica.get(0).equals("TYPE") && verifica.get(1).equals("AUTH") &&
                    verifica.get(2).equals("UC") && verifica.get(4).equals("PASSWORD") && verifica.size()==6){

                if (!(this.eleitor.getPassword().equals(verifica.get(5)))){
                    this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
                    closeSocket();
                }
                StringBuilder booletim = new StringBuilder("");
                int i = 0;
                try{
                    this.votosLista = rmi.getListaCandidatas(this.eleicaoID, this.eleitor); // listas candidatas
                }catch (Exception e){ // fail over
                    reconectarRMI(rmi);
                    this.votosLista = rmi.getListaCandidatas(this.eleicaoID, this.eleitor);
                }

                booletim.append("\tPasso 3: Votar numa das listas do boletim (preencher nos '*')" + "\t'type|vote;option|*numero da lista*'\nTYPE|BULLETIN");
                if (this.votosLista != null) {
                    for (ListaCandidata aux : this.votosLista)
                        booletim.append(";").append(aux.getNome()).append("|").append(++i);
                }
                this.out.writeUTF(booletim.toString());

                //----- TYPE/VOTE voto do eleitor -----
                if ((verifica = verifica(this.in))!=null && verifica.get(0).equals("TYPE") &&
                        verifica.get(1).equals("VOTE") && verifica.get(2).equals("OPTION") && verifica.size()==4) {
                    i = Integer.parseInt(verifica.get(3))-1;

                    Voto voto = new Voto(this.eleitor, this.votosLista.get(i), this.departamento);
                    rmi.votacaoEleitor(this.eleicaoID, voto);
                    this.out.writeUTF("TYPE|VOTE;VOTE|CONFIRMED");
                }else
                    this.out.writeUTF("TYPE|VOTE;OPTION|UNACKNOWLEDGED");
            }else
                this.out.writeUTF("TYPE|AUTH;CREDENTIALS|NOT MATCH");
            closeSocket();
        }catch(EOFException e){System.out.println("EOF:" + e);
        }catch(IOException e){System.out.println();
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}

