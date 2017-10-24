package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Docente extends Pessoa implements Serializable {

    public Docente(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        lista.add(this);
    }

    //adicionar voto na geral
    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto) {
        dg.addVotoDocente(voto);
    }

    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorDocente(voto);
    }

    @Override
    public void AddEleitorGeral(DirecaoGeral dg) {
        dg.AddDocente(this);
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosDocentes();
    }
}