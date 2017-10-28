package RMI;

import Classes.*;

import java.rmi.Remote;
import java.util.ArrayList;

public interface TCPserverRMIimplements extends Remote {

    Departamento abrirMesaVoto(String dep) throws java.rmi.RemoteException;

    void fecharMesaVoto(String dep) throws java.rmi.RemoteException;

    Pessoa identificarEleitor(String numerocc) throws java.rmi.RemoteException;

    ArrayList<Eleicao> identificarEleicoes(Pessoa eleitor, Departamento dep) throws java.rmi.RemoteException;

    Eleicao escolherEleicao(Pessoa eleitor, Departamento dep, int i) throws java.rmi.RemoteException;

    ArrayList<ListaCandidata> getListaCandidatas(Eleicao eleicao, Pessoa eleitor) throws java.rmi.RemoteException;

    void votacaoEleitor(Eleicao eleicao, Voto voto) throws java.rmi.RemoteException;

    void sayHello() throws java.rmi.RemoteException;
}