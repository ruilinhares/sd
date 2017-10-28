package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe que deriva da classe pessoa. Represeta um estudante.
 */
public class Estudante extends Pessoa implements Serializable {

    /**
     * Construtor da classe
     * @param nome Nome do estudante
     * @param numero numero da UC do estudante
     * @param telemovel telemovel do estudante
     * @param morada morada do estudante
     * @param password password do estudante
     * @param departamento departamento do estudante
     * @param numeroCC numero do cartao de cidadao do estudante
     * @param validade validade do CC estudante
     */
    public Estudante(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    /**
     * Metodo que permite inserir uma lista de pessoas num departamento.
     * @param lista lista de pessoas a inserir
     * @param dep departamento onde queremos registar as pessoas
     */
    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        dep.addEstudante(this);
        lista.add(this);
    }

    /**
     * Metodo que permite adicionar um voto numa eleição.
     * @param dg Eeição onde queremos inserir o voto.
     * @param voto voto a ser inserido.
     */
    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto){
        dg.addVotoEstudante(voto);
    }

    /**
     *Metodo que permite retirar um eleitor da lista de eleitores após votar.
     * @param dg Eeição onde queremos retirar o eleitor.
     * @param voto voto do eleitor.
     */

    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorEstudante(voto);
    }

    /**
     * Metodo que permite inserir um eleitor numa votacao da direcao geral.
     * @param dg Eeição onde queremos inserir o eleitor.
     */
    @Override
    public void AddEleitorGeral(DirecaoGeral dg) {
        dg.AddEstudante(this);
    }

    /**
     * Meotodo que permite obter uma lista de estudantes candidata da direcao geral
     * @param dg eleicao onde queremos obter a lista de candidatos
     * @return lista de candidatos
     */
    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosEstudantes();
    }

    /**
     * Metodo to String.
     * @return string com informação do estudante.
     */
    @Override
    public String toString() {
        return "Estudante{}";
    }

    public void print(){
        System.out.println(super.getNome());
        System.out.println(super.getNumeroCC());
        System.out.println(super.getNumeroUC());
        System.out.println(super.getPassword());
        System.out.println(super.getDepartamento().getNome());
    }
}
