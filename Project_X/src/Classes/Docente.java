package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe que representa um docente
 */
public class Docente extends Pessoa implements Serializable {

    /**
     * Construtor da classe
     * @param nome Nome do docente
     * @param numero numero da UC do docente
     * @param telemovel telemovel do docente
     * @param morada morada do docente
     * @param password password do docente
     * @param departamento departamento do docente
     * @param numeroCC numero do cartao de cidadao do docente
     * @param validade validade do CC do docente
     */
    public Docente(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        super(nome, numero, telemovel, morada, password, departamento, numeroCC, validade);
    }

    /**
     * Método que permite inserir uma lista de pessoas num departamento
     * @param lista lista de pessoas a inserir
     * @param dep departamento onde queremos registar as pessoas
     */
    @Override
    public void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep){
        lista.add(this);
    }

    /**
     * Método que permite adicionar o voto de um eleitor numa eleição da direção geral
     * @param dg Eeição onde queremos inserir o voto.
     * @param voto voto a ser inserido.
     */
    @Override
    public void addVotoGeral(DirecaoGeral dg, Voto voto) {
        dg.addVotoDocente(voto);
    }

    /**
     * Método que permite remover um eleitor da lista de eleitores depois deste votar.
     * @param dg Eeição onde queremos retirar o eleitor.
     * @param voto voto do eleitor.
     */
    @Override
    public void removerEleitorGeral(DirecaoGeral dg, Voto voto) {
        dg.removeEleitorDocente(voto);
    }

    /**
     * Método que permite inserir um eleitor numa eleição
     * @param dg Eeição onde queremos inserir o eleitor.
     */
    @Override
    public void AddEleitorGeral(DirecaoGeral dg) {
        dg.AddDocente(this);
    }

    /**
     * Método que permite obter a lista de candidatos de uma eleição
     * @param dg Eleição da direção geral
     * @return Lista de candidatos da eleição
     */
    @Override
    public ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg) {
        return dg.getListaCandidatosDocentes();
    }

    public void print(){
        System.out.println(super.getNome());
        System.out.println(super.getNumeroCC());
        System.out.println(super.getDepartamento().getNome());
    }

}