package RMI;

import Classes.*;
import TCP.TCPServer;

import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class RMIserver extends UnicastRemoteObject implements AdminRMIimplements, TCPserverRMIimplements {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Eleicao> listaEleicoes;
    private ArrayList<Departamento> listaDepartamentos;
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<TCPServer> mesasVotos;

    RMIserver() throws RemoteException {
        super();
    }

    private RMIserver(ArrayList<Eleicao> listaEleicoes, ArrayList<Departamento> listaDepartamentos, ArrayList<Pessoa> listaPessoas) throws RemoteException {
        super();
        this.listaEleicoes = listaEleicoes;
        this.listaDepartamentos = listaDepartamentos;
        this.listaPessoas = listaPessoas;
        this.mesasVotos = new ArrayList<>();
        listaDepartamentos.add(new Departamento("nei",new ArrayList<>()));
        listaPessoas.add(new Estudante("ze1","1","1","1","123",listaDepartamentos.get(0),"123","123"));
        listaPessoas.add(new Estudante("ze2","1","1","1","123",listaDepartamentos.get(0),"123","123"));
        this.mesasVotos.add(new TCPServer(listaDepartamentos.get(0)));
    }


    public ArrayList<Eleicao> getListaEleicoes()  {
        return listaEleicoes;
    }

    public ArrayList<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public ArrayList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    public  void addDepartamento(Departamento dep){
        this.listaDepartamentos.add(dep);
    }


    public void sayHello() throws RemoteException {
        System.out.println("print do lado do servidor...!.");
    }

    // GET ELEICAO
    public Eleicao getEleicao(String eleicao) throws RemoteException{
        for (Eleicao aux : listaEleicoes)
            if (eleicao.equals(aux.getTitulo()))
                return aux;
        return null;
    }

    // GET Departamento
    public Departamento getDepartamento(String dep) throws RemoteException{
        for (Departamento aux : listaDepartamentos)
            if (dep.equals(aux.getNome()))
                return aux;
        return null;
    }

    // Ponto 1 - REGISTAR PESSOA
    public Boolean registarPessoa (Pessoa pessoa) throws RemoteException{

        for (Pessoa aux: this.listaPessoas)
            if (pessoa.getNumeroUC().equals(aux.getNumeroUC())
                    || pessoa.getNumeroCC().equals(aux.getNumeroUC()))
                return false;

        for (Departamento dep : listaDepartamentos)
            if (pessoa.getDepartamento().equals(dep)) {
                pessoa.inserirPessoaNaLista(listaPessoas, dep);
                return true;
            }
        return false;
    }

    // Ponto 2 - GERIR DEPARTAMENTOS E FALCULDADES
    public void teste(){
        ArrayList<Estudante> listaEstudantes = new ArrayList<>();
        Departamento dep= new Departamento( "NEI", listaEstudantes );
        this.listaDepartamentos.add(dep);
    }

    // Ponto 3 - CRIAR ELEICOES
    public void criarEleicao(Eleicao eleicao) throws RemoteException{
        listaEleicoes.add(eleicao);
    }

    @Override
    public void AddEleicao(Eleicao eleicao) throws RemoteException {
        this.listaEleicoes.add(eleicao);
    }

    // Ponto 4 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO


    // Ponto 5 - GERIR MESAS DE VOTOS

    //Adicionar Mesa de Voto
    //public void adicionarMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException
    //Remover Mesa de Voto
    //public void removerMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException

    //-----TCPserver-client-------------------------------------

    // ABRIR MESA DE VOTO
    public Departamento abrirMesaVoto(String dep){
        for (TCPServer aux : mesasVotos)
            if (dep.equals(aux.getDepartamento().getNome()) && !aux.getEstadoMesa()) {
                aux.setEstadoMesa(true);
                return aux.getDepartamento();
            }
        return null;
    }

    // Ponto 6 IDENTIFICAR UM ELEITOR
    @Override
    public Pessoa identificarEleitor(String numerocc, Departamento dep) throws RemoteException{
        for (TCPServer mesaux : this.mesasVotos)
            if (mesaux.getDepartamento().getNome().equals(dep.getNome()))
                for (Eleicao aux: mesaux.getListaEleicoes())
                    if (aux.verificaVotacao())
                        for (Pessoa pessoa: aux.getListaEleitores())
                            if (pessoa.getNumeroCC().equals(numerocc))
                                return pessoa;
        return null;
    }

    // devolver lista de Eleicoes que o eleitor pode votar
    @Override
    public ArrayList<Eleicao> identificarEleicoes(Pessoa eleitor, Departamento dep){
        ArrayList<Eleicao> eleicoes = new ArrayList<>();
        for (TCPServer mesaux : this.mesasVotos)
            if (mesaux.getDepartamento().getNome().equals(dep.getNome()))
                for (Eleicao aux: mesaux.getListaEleicoes())
                    if (aux.verificaVotacao())
                        for (Pessoa pessoa: aux.getListaEleitores())
                            if (pessoa.getNumeroCC().equals(eleitor.getNumeroCC()))
                                eleicoes.add(aux);
        return eleicoes;
    }

    public int escolherEleicao(Pessoa eleitor, Departamento dep, int i){
        int count = 0;
        for (TCPServer mesaux : this.mesasVotos)
            if (mesaux.getDepartamento().getNome().equals(dep.getNome()))
                for (Eleicao aux: mesaux.getListaEleicoes())
                    if (aux.verificaVotacao())
                        for (Pessoa pessoa: aux.getListaEleitores())
                            if (pessoa.getNumeroCC().equals(eleitor.getNumeroCC()) && (++count)==i)
                                return aux.hashCode();
        return -1;
    }
    // Ponto 7 AUTENTICAR ELEITOR

    // Ponto 8 (VALIDAR O VOTO DO ELEITOR)
    @Override
    public void votacaoEleitor(int eleicaohashcode, Voto voto) throws RemoteException {
        for (Eleicao auxEleicao : this.listaEleicoes) {
            if (eleicaohashcode == auxEleicao.hashCode()) {
                auxEleicao.removeEleitor(voto);
                auxEleicao.addVoto(voto);
            }
        }
    }

    // devolver a lista de Candidatos correspondente ao eleitor
    @Override
    public ArrayList<ListaCandidata> getListaCandidatas(int eleicaohashcode, Pessoa eleitor) throws RemoteException{
        for (Eleicao auxEleicao : this.listaEleicoes)
            if (eleicaohashcode==auxEleicao.hashCode()){
                return auxEleicao.getListaCandidatos(eleitor);
            }
        return null;
    }

    //---UDP----------------------------------------------------

    private void udpServerON(){
        new Thread(new UDPServer()).start();
    }

    // =========================================================
    public static void main(String args[]) throws RemoteException {

        RMIserver rmiServer = null;
        try {
            rmiServer = new RMIserver(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Registry rmiRegistry = LocateRegistry.createRegistry(6789);
            rmiRegistry.rebind("HelloRMI", rmiServer); // RMI primario iniciado
            rmiServer.udpServerON();
            System.out.println("RMI Primário Server ready.");

        } catch (RemoteException re) { //AccessException
            System.out.println("Remote Exception no RMI Server: " + re.getMessage()+"\nRMI Backup...");
            // Criar ligação com RMI primário
            try {
            //  -------------UDP-Client--------------------
                DatagramSocket clientSocket = null;
                try{
                    clientSocket = new DatagramSocket();
                    while (true) {
                        InetAddress IPAddress = InetAddress.getByName("localhost");
                        byte[] sendData;
                        byte[] receiveData = new byte[1024];
                        sendData = "ok?".getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6000);
                        clientSocket.send(sendPacket); // set timeout de resposta
                        clientSocket.setSoTimeout(5000); // receber resposta no timeout
                        try {
                            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                            clientSocket.receive(receivePacket);
                            Thread.sleep(5000); // tempo de espera da resposta
                            System.out.println("UDP SERVER:" + new String(receivePacket.getData(), 0, receivePacket.getLength()));
                        } catch (SocketTimeoutException e) { // socket timeout exception
                            // RMI Server primário foi a baixo
                            // RMI Backup assume o controlo
                            clientSocket.close();
                            System.out.println("Timeout ultrapassado! " + e.getMessage());
                            try {
                                if (rmiServer == null) {
                                    System.out.println("Erro!");
                                    exit(0);
                                }
                                Registry rmiRegistry = LocateRegistry.createRegistry(6789); // RMI Backup iniciado
                                rmiRegistry.rebind("HelloRMI", rmiServer);
                                rmiServer.udpServerON();
                                System.out.println("RMI Backup Server ready.");
                            } catch (RemoteException re2) {
                                System.out.println(re2.getMessage());
                            }
                        }
                    } //while
                }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
                }catch (IOException e){System.out.println("IO: " + e.getMessage());
                }finally {if(clientSocket != null) clientSocket.close();}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// UDP-SERVER====================================================================

class UDPServer implements Runnable
{
    UDPServer() {}

    public void run()
    {
        DatagramSocket serverSocket = null;
        try{
            serverSocket = new DatagramSocket(6000);
            byte[] receiveData = new byte[1024];
            byte[] sendData;
            while(true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String( receivePacket.getData());
                System.out.println("UDPServer recebeu: " + sentence);
                sendData = "im ok".getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            } //while
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());

        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(serverSocket != null) serverSocket.close();}
    }
}