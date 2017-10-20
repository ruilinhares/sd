package RMI;

import Classes.*;

import java.rmi.Remote;
import java.util.ArrayList;

public interface TCPserverRMIimplements extends Remote {



    public Pessoa identificarEleitor(int eleicaohashcode, String numerouc) throws java.rmi.RemoteException;

    public ArrayList<ListaCandidata> getListaCandidatas(int eleicaohashcode, Pessoa eleitor) throws java.rmi.RemoteException;

    public void votacaoEleitor(int eleicaohashcode, Voto voto) throws java.rmi.RemoteException;

    public String sayHello() throws java.rmi.RemoteException;
}
