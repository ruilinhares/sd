import java.rmi.Remote;

public interface TCPserverRMIimplements extends Remote {

    public Pessoa identificarEleitor(Eleicao eleicao, String numerouc) throws java.rmi.RemoteException;

    public void votacaoEleitor(Eleicao eleicao, Voto voto) throws java.rmi.RemoteException;

    public String sayHello() throws java.rmi.RemoteException;
}
