package RMI;
import Classes.*;

import java.rmi.*;
import java.util.ArrayList;

public interface AdminRMIimplements extends Remote {

	public ArrayList<Eleicao> getListaEleicoes() throws java.rmi.RemoteException;

    public ArrayList<Departamento> getListaDepartamentos() throws java.rmi.RemoteException;

    public ArrayList<Pessoa> getListaPessoas() throws java.rmi.RemoteException;

    public  void addDepartamento(Departamento dep) throws java.rmi.RemoteException;

    public Boolean registarPessoa (Pessoa pessoa) throws java.rmi.RemoteException;

    public void criarEleicao(Eleicao eleicao) throws java.rmi.RemoteException;

    public String sayHello() throws java.rmi.RemoteException;
}
