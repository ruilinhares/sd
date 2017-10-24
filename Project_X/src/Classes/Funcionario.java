package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Funcionario extends Pessoa implements Serializable {
    public Funcionario(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        lista.add(this);
    }

    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto) {
        dg.addVotoFuncionario(voto);
    }

    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorFuncionario(voto);
    }

    @Override
    public void AddEleitorGeral(DirecaoGeral dg) {
        dg.AddFuncionario(this);
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosFuncionarios();
    }

    @Override
    public String toString() {
        return "Funcionario{}";
    }
}
