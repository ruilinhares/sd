package Classes;

import java.io.Serializable;
import java.util.*;

public class Departamento implements Serializable{
    private String nome;
    private ArrayList<Estudante> ListaEstudantes;

    public Departamento(String nome, ArrayList<Estudante> listaEstudantes) {
        this.nome = nome;
        ListaEstudantes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Estudante> getListaEstudantes() {
        return this.ListaEstudantes;
    }

    public void setListaEstudantes(ArrayList<Estudante> listaEstudantes) {
        ListaEstudantes = listaEstudantes;
    }

    public void addEstudante(Estudante estudante) {
        ListaEstudantes.add(estudante);
    }
}
