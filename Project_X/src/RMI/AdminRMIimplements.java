package RMI;
import Classes.*;

import java.rmi.*;
import java.util.ArrayList;

public interface AdminRMIimplements extends Remote {

	ArrayList<Eleicao> getListaEleicoes() throws java.rmi.RemoteException;

    ArrayList<Departamento> getListaDepartamentos() throws java.rmi.RemoteException;

    ArrayList<Pessoa> getListaPessoas() throws java.rmi.RemoteException;

    void addDepartamento(Departamento dep) throws java.rmi.RemoteException;

    Boolean registarPessoa(Pessoa pessoa) throws java.rmi.RemoteException;

    void criarEleicao(Eleicao eleicao) throws java.rmi.RemoteException;

    void teste() throws RemoteException;

    void AddEleicao(Eleicao eleicao) throws RemoteException;

    void sayHello() throws java.rmi.RemoteException;
}
