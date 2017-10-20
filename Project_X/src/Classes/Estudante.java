package Classes;

import java.util.ArrayList;

public class Estudante extends Pessoa{
    public Estudante(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        dep.addEstudante(this);
        lista.add(this);
    }

    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto){
        dg.addVotoEstudante(voto);
    }

    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorEstudante(voto);
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosEstudantes();
    }
}
