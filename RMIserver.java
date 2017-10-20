import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;

public class RMIserver extends UnicastRemoteObject implements AdminRMIimplements {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Eleicao> listaEleicoes;
    private ArrayList<Departamento> listaDepartamentos;
    private ArrayList<Pessoa> listaPessoas;

    public RMIserver( ArrayList<Eleicao> listaEleicoes, ArrayList<Departamento> listaDepartamentos, ArrayList<Pessoa> listaPessoas) throws RemoteException {
        super();
        this.listaEleicoes = listaEleicoes;
        this.listaDepartamentos = listaDepartamentos;
        this.listaPessoas = listaPessoas;
    }


    public ArrayList<Eleicao> getListaEleicoes()  {
        return listaEleicoes;
    }

    public void AddEleicao(Eleicao eleicao){
        this.listaEleicoes.add(eleicao);
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

    public String sayHello() throws RemoteException {
        System.out.println("print do lado do servidor...!.");

        return "Hello, World!";
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
            if (pessoa.getDepartamento().equals(dep.getNome())) {
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

    // Ponto 4 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO


    // Ponto 5 - GERIR MESAS DE VOTOS

    //Adicionar Mesa de Voto
    /*public void adicionarMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException {
        eleicao.addMesaVoto(mesa);
    }

    //Remover Mesa de Voto
    public void removerMesaVoto(Eleicao eleicao, MesaVoto mesa) throws RemoteException {
        eleicao.addMesaVoto(mesa);
    }*/

    // Ponto 6 IDENTIFICAR UM ELEITOR
    public Pessoa identificarEleitor(String numero_uc) throws RemoteException{ //ponto6
        for (Pessoa pessoa: listaPessoas) {
            if (pessoa.getNumeroUC().equals(numero_uc))
                return pessoa;
        }
        return null;
    }
    // Ponto 8 (VALIDAR O VOTO DO ELEITOR)
    public Boolean votoEleitor(Pessoa pessoa) throws RemoteException{
        return false;
    }

    // =========================================================
    public static void main(String args[]) {

        try {
            AdminRMIimplements rmi = new RMIserver(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Registry rmiRegistry = LocateRegistry.createRegistry(6789);
            rmi.teste();
            rmiRegistry.rebind("HelloRMI", rmi);
            System.out.println("Hello Server ready.");
        } catch (RemoteException re) {
            System.out.println("Exception in HelloImpl.main: " + re);
        }
    }

}
