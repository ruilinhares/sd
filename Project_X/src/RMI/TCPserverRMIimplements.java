package RMI;

import Classes.*;

import java.rmi.Remote;
import java.util.ArrayList;

public interface TCPserverRMIimplements extends Remote {



    Pessoa identificarEleitor(int eleicaohashcode, String numerouc) throws java.rmi.RemoteException;

    ArrayList<ListaCandidata> getListaCandidatas(int eleicaohashcode, Pessoa eleitor) throws java.rmi.RemoteException;

    void votacaoEleitor(int eleicaohashcode, Voto voto) throws java.rmi.RemoteException;

    void sayHello() throws java.rmi.RemoteException;
}
