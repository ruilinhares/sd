package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe que representa um funcionário.
 */
public class Funcionario extends Pessoa implements Serializable {
    /**
     * Construtor da classe
     * @param nome Nome do funcionário
     * @param numero Número da UC do funcionário
     * @param telemovel Número de telemóvel do funcionário
     * @param morada Morada do funcionário
     * @param password Password do utilizador
     * @param departamento Departamento do funcionário
     * @param numeroCC Número do cartão de cidadão do funcionário
     * @param validade Validade do cartão de cidadão do funcionário
     */
    public Funcionario(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    /**
     * Método que permite inserir um funcionário num departamento
     * @param lista lista de pessoas a inserir
     * @param dep departamento onde queremos registar as pessoas
     */
    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        lista.add(this);
    }

    /**
     * Método que permite inserir um voto de um funcionário de uma eleição geral.
     * @param dg Eeição onde queremos inserir o voto.
     * @param voto voto a ser inserido.
     */
    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto) {
        dg.addVotoFuncionario(voto);
    }

    /**
     * Método que permite remover um funcionário da lista de eleitores após este ter votado.
     * @param dg Eeição onde queremos retirar o eleitor.
     * @param voto voto do eleitor.
     */
    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorFuncionario(voto);
    }

    /**
     * Método que permite adicionar um funcionário à lista de eleitores de uma eleição da direção geral.
     * @param dg Eeição onde queremos inserir o eleitor.
     */
    @Override
    public void AddEleitorGeral(DirecaoGeral dg) {
        dg.AddFuncionario(this);
    }

    /**
     * Método que permite obter uma lista candidata de funcionários.
     * @param dg Eleição onde queremos ir buscar a eleição.
     * @return Lista de candidatos da eleição
     */
    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosFuncionarios();
    }

    /**
     * Método que permite obter a informação de um funcionário.
     * @return String com a informação do funcionário.
     */
    @Override
    public String toString() {
        return "Funcionario{}";
    }

    public void print(){
        System.out.println(super.getNome());
        System.out.println(super.getNumeroCC());
        System.out.println(super.getDepartamento().getNome());
    }
}
