package Classes;

import java.io.Serializable;
import java.util.*;

/**
 *  Esta classe contem o construtor departamento e os metodos para definir os diferentes atributos .
 * @author 007
 */

public class Departamento implements Serializable{

    /**
     * Atributo que representa o nome do departamento.
     */
    private String nome;

    /**
     * Atributo que todos os estudantes desse departamento e que podem votar em eleições desse departamento.
     */
    private ArrayList<Estudante> ListaEstudantes;

    /**
     * Construtor da classe.
     * @param nome Nome do departamento.
     * @param listaEstudantes Lista de Estudantes desse departamento.
     */
    public Departamento(String nome, ArrayList<Estudante> listaEstudantes) {
        this.nome = nome;
        ListaEstudantes = new ArrayList<>();
    }

    /**
     * Metodo que permite obter o nome do Departamento.
     * @return Nome do Departamento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que permite atribuir um nome ao departamento.
     * @param nome Nome que pretendemos atribuir ao departamento.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo que permite obter a lista de estudantes do departamento.
     * @return  Lista de Estudantes desse departamento.
     */
    ArrayList<Estudante> getListaEstudantes() {
        return this.ListaEstudantes;
    }

    /**
     * Metodo que permite atribuir uma lista de estudantes ao departamento..
     * @param listaEstudantes Lista de estudantes que pretendemos atribuir ao departamento.
     */
    public void setListaEstudantes(ArrayList<Estudante> listaEstudantes) {
        ListaEstudantes = listaEstudantes;
    }

    /**
     * Metodo que permite adicionar um estudante à lista de estudantes do departamento..
     * @param estudante Estudante que pretendemos adicionar.
     */
    void addEstudante(Estudante estudante) {
        ListaEstudantes.add(estudante);
    }

    public void print(){
        System.out.println(this.nome);
    }
}
