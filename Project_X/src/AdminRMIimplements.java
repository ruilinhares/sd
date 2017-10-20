import java.rmi.*;
import java.util.ArrayList;

public interface AdminRMIimplements extends Remote {

    public ArrayList<Eleicao> getListaEleicoes() throws RemoteException;

    public ArrayList<Departamento> getListaDepartamentos() throws RemoteException;

    public ArrayList<Pessoa> getListaPessoas() throws RemoteException;

    public void addDepartamento(Departamento dep) throws RemoteException;

    public Boolean registarPessoa(Pessoa pessoa) throws RemoteException;

    public void criarEleicao(Eleicao eleicao) throws RemoteException;

    public String sayHello() throws RemoteException;

    public void teste() throws RemoteException;

    public void AddEleicao(Eleicao eleicao) throws RemoteException;

}