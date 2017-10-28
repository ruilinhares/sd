package RMI;
import java.io.*;
import Classes.*;
import TCP.TCPServer;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class RMIserver extends UnicastRemoteObject implements AdminRMIimplements, TCPserverRMIimplements {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Eleicao> listaEleicoes;
    private ArrayList<Departamento> listaDepartamentos;
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<TCPServer> mesasVotos;

    private RMIserver() throws RemoteException {
        super();
        this.listaEleicoes = new ArrayList<>();
        this.listaDepartamentos = new ArrayList<>();
        this.listaPessoas = new ArrayList<>();
        this.mesasVotos = new ArrayList<>();

        this.start();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2016,8,25,11,0);
        c2.set(2017,10,29,11,0);
        ArrayList<ListaCandidata> l1 = new ArrayList<>();
        l1.add(new ListaCandidata("Lista A"));

        listaEleicoes.add(new DirecaoGeral("GERAL","dg",c1,c2,new ArrayList<>(l1),new ArrayList<>(l1),new ArrayList<>(l1),listaPessoas));
        mesasVotos.add(new TCPServer(listaDepartamentos.get(0)));
        mesasVotos.get(0).addEleicao(listaEleicoes.get(2));
        for (Pessoa e : listaPessoas)
            System.out.println(e.getNumeroCC());
    }

    synchronized public ArrayList<Eleicao> getListaEleicoes()  {
        return listaEleicoes;
    }

    synchronized public ArrayList<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    synchronized public ArrayList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    synchronized public ArrayList<TCPServer> getMesasVotos(){
        return mesasVotos;
    }

    public  void addDepartamento(Departamento dep){
        this.listaDepartamentos.add(dep);
    }

    synchronized public void sayHello() throws RemoteException {
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
    synchronized public void RemoveDepartamento(int i){
        this.listaDepartamentos.remove(i);
    }

    synchronized public void AddDepartamento(Departamento dep){
        this.listaDepartamentos.add(dep);
    }
    // Ponto 1 - REGISTAR PESSOA
    synchronized public Boolean registarPessoa (Pessoa pessoa) throws RemoteException{

        for (Pessoa aux: this.listaPessoas)
            if (pessoa.getNumeroUC().equals(aux.getNumeroUC())
                    || pessoa.getNumeroCC().equals(aux.getNumeroUC()))
                return false;

        for (Departamento dep : listaDepartamentos)
            if ((pessoa.getDepartamento().getNome().toUpperCase()).equals(dep.getNome().toUpperCase())) {
                pessoa.inserirPessoaNaLista(listaPessoas, dep);
                return true;
            }
        return false;
    }

    // Ponto 2 - GERIR DEPARTAMENTOS E FALCULDADES
    synchronized public void teste(){
        ArrayList<Estudante> listaEstudantes = new ArrayList<>();
        Departamento dep= new Departamento( "NEI", listaEstudantes );
        this.listaDepartamentos.add(dep);
    }

    // Ponto 3 - CRIAR ELEICOES
    synchronized public void criarEleicao(Eleicao eleicao) throws RemoteException{
        listaEleicoes.add(eleicao);
    }

    @Override
    synchronized public void AddEleicao(Eleicao eleicao) throws RemoteException {
        this.listaEleicoes.add(eleicao);
    }

    synchronized public void RemoveEleicao(int i){
        this.listaEleicoes.remove(i);
    }

    synchronized public void RemoveMesa(int i){
        this.mesasVotos.remove(i);
    }

    // Ponto 4 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO


    // Ponto 5 - GERIR MESAS DE VOTOS

    //Adicionar Mesa de Voto
    //public void adicionarMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException
    //Remover Mesa de Voto
    //public void removerMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException

    //-----TCPserver-client-------------------------------------

    // ABRIR MESA DE VOTO
    synchronized public Departamento abrirMesaVoto(String dep){
        for (TCPServer aux : mesasVotos)
            if ((dep.toUpperCase()).equals(aux.getDepartamento().getNome().toUpperCase()) && !aux.getEstadoMesa()) {
                aux.setEstadoMesa(true);
                return aux.getDepartamento();
            }
        return null;
    }

    // ABRIR MESA DE VOTO
    synchronized public void fecharMesaVoto(String dep){
        for (TCPServer aux : mesasVotos)
            if (dep.equals(aux.getDepartamento().getNome()) && aux.getEstadoMesa()) {
                aux.setEstadoMesa(false);
                return;
            }
    }

    // Ponto 6 IDENTIFICAR UM ELEITOR
    @Override
    synchronized public Pessoa identificarEleitor(String numerocc) throws RemoteException{

        for (Pessoa pessoa: this.listaPessoas)
            if (pessoa.getNumeroCC().equals(numerocc))
                return pessoa;
        return null;
    }

    // devolver lista de Eleicoes que o eleitor pode votar
    @Override
    synchronized public ArrayList<Eleicao> identificarEleicoes(Pessoa eleitor, Departamento dep){
        ArrayList<Eleicao> eleicoes = new ArrayList<>();
        for (TCPServer mesaux : this.mesasVotos)
            if ((mesaux.getDepartamento().getNome().toUpperCase()).equals(dep.getNome().toUpperCase()))
                for (Eleicao aux: mesaux.getListaEleicoes())
                    if (aux.verificaVotacao())
                        for (Pessoa pessoa: aux.getListaEleitores())
                            if (pessoa.getNumeroCC().equals(eleitor.getNumeroCC()))
                                eleicoes.add(aux);
        return eleicoes;
    }

    //adiciona uma mesa criada a lista de mesas de voto
    synchronized public void AddMesa(TCPServer mesa){
        mesasVotos.add(mesa);
    }

    synchronized public int escolherEleicao(Pessoa eleitor, Departamento dep, int i){
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

    // devolver a lista de Candidatos correspondente ao eleitor
    @Override
    synchronized public ArrayList<ListaCandidata> getListaCandidatas(int eleicaohashcode, Pessoa eleitor) throws RemoteException{
        for (Pessoa pessoa: this.listaPessoas)
            if (pessoa.getNumeroCC().equals(eleitor.getNumeroCC()))
                for (Eleicao auxEleicao : this.listaEleicoes)
                    if (eleicaohashcode==auxEleicao.hashCode())
                        return auxEleicao.getListaCandidatos(pessoa);

        return null;
    }
    // Ponto 8 (VALIDAR O VOTO DO ELEITOR)
    @Override
    synchronized public void votacaoEleitor(int eleicaohashcode, Voto voto) throws RemoteException {
        for (Pessoa pessoa: this.listaPessoas)
            if (pessoa.getNumeroCC().equals(voto.getEleitor().getNumeroCC()))
                for (Eleicao auxEleicao : this.listaEleicoes) {
                    if (eleicaohashcode == auxEleicao.hashCode()) {
                        auxEleicao.removeEleitor(voto);
                        auxEleicao.addVoto(voto);
                    }
        }
    }

    //---UDP----------------------------------------------------

    private void udpServerON(){
        new Thread(new UDPServer()).start();
    }

    //---Base-Dados-----
    public void start() {
        ObjectInputStream objectinputstream1 = null;
        ObjectInputStream objectinputstream2 = null;
        ObjectInputStream objectinputstream3 = null;
        try {

            FileInputStream streamIn = new FileInputStream("pessoas.ser");
            objectinputstream1 = new ObjectInputStream(streamIn);
            ArrayList<Pessoa> people = (ArrayList<Pessoa>) objectinputstream1.readObject();
            this.listaPessoas=people;
            streamIn = new FileInputStream("departamentos.ser");
            objectinputstream1 = new ObjectInputStream(streamIn);
            ArrayList<Departamento> deps = (ArrayList<Departamento>) objectinputstream1.readObject();
            this.listaDepartamentos=deps;
            streamIn = new FileInputStream("eleicoes.ser");
            objectinputstream1 = new ObjectInputStream(streamIn);
            ArrayList<Eleicao> elections = (ArrayList<Eleicao>) objectinputstream1.readObject();
            this.listaEleicoes=elections;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream1 != null && objectinputstream2 != null && objectinputstream3 != null) {
                try {
                    objectinputstream1.close();
                    objectinputstream2.close();
                    objectinputstream3.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Store(){
        ObjectOutputStream oos1=null,oos2=null,oos3=null;
        FileOutputStream fout1,fout2,fout3;
        try{
            fout1 = new FileOutputStream("pessoas.ser");
            oos1 = new ObjectOutputStream(fout1);
            oos1.writeObject(listaPessoas);
            fout2 = new FileOutputStream("departamentos.ser");
            oos2 = new ObjectOutputStream(fout2);
            oos2.writeObject(listaDepartamentos);
            fout3 = new FileOutputStream("eleicoes.ser");
            oos3 = new ObjectOutputStream(fout3);
            oos3.writeObject(listaEleicoes);
        }catch (IOException e) {
            System.out.println("IOEXCEPTION");
        }finally {
            if(oos1  != null && oos2!=null && oos3!=null){
                try {
                    oos1.close();
                    oos2.close();
                    oos3.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //---UDP----------------------------------------------------


    // =========================================================
    public static void main(String args[]) throws RemoteException {

        RMIserver rmiServer = null;
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(6789);
            rmiServer = new RMIserver();
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
                            System.out.println("RMI primario:" + new String(receivePacket.getData(), 0, receivePacket.getLength()));
                        } catch (SocketTimeoutException e) { // socket timeout exception                            // RMI Server primário foi a baixo
                            // RMI Backup assume o controlo
                            clientSocket.close();
                            System.out.println("Timeout ultrapassado! " + e.getMessage());
                            try {
                                rmiServer = new RMIserver();
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