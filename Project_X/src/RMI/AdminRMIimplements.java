package RMI;
import Classes.*;
import TCP.TCPServer;

import java.rmi.*;
import java.util.ArrayList;

public interface AdminRMIimplements extends Remote {

	ArrayList<Eleicao> getListaEleicoes() throws java.rmi.RemoteException;

    ArrayList<Departamento> getListaDepartamentos() throws java.rmi.RemoteException;

    ArrayList<Pessoa> getListaPessoas() throws java.rmi.RemoteException;

    ArrayList<TCPServer> getMesasVotos() throws java.rmi.RemoteException;

    void addDepartamento(Departamento dep) throws java.rmi.RemoteException;

    Boolean registarPessoa(Pessoa pessoa) throws java.rmi.RemoteException;

    void criarEleicao(Eleicao eleicao) throws java.rmi.RemoteException;

    void AddEleicao(Eleicao eleicao) throws java.rmi.RemoteException;

    void AddMesa(TCPServer mesa) throws java.rmi.RemoteException;

    void sayHello() throws java.rmi.RemoteException;

    void RemoveDepartamento(int i) throws java.rmi.RemoteException;

    void RemoveEleicao(int i) throws java.rmi.RemoteException;

    void RemoveMesa(int i) throws java.rmi.RemoteException;

}
