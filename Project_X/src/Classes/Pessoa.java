package Classes;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Esta classe e uma classe abstrata e contem o construtor pessoa e os metodos para definir os diferentes atributos.
 */
public abstract class Pessoa implements Serializable {
    /**
     * Atributo que representa o nome da pessoa.
     */
    private String Nome;
    /**
     * Atributo que representa o numero da uc da pessoa
     */
    private String NumeroUC;
    /**
     * Atributo que representa o numero de telemovel da pessoa.
     */
    private String Telemovel;
    /**
     * Atributo que representa a morada da pessoa.
     */
    private String Morada;
    /**
     * Atributo que representa a password do utilizador.
     */
    private String Password;
    /**
     * Atributo que representa o departamento onde a pessoa esta registada.
     */
    private Departamento Departamento;/**
     * Atributo que representa o numero do cartao de cidadao.
     */
    private String NumeroCC;
    /**
     * Atributo que representa a validade do cartao de cidadao.
     */
    private String Validade;

    /**
     * Construtor da classe
     * @param nome Nome da pessoa
     * @param numero Numero da UC
     * @param telemovel Numero de telemovel
     * @param morada Morada da pessoa
     * @param password Password do utilizador
     * @param numeroCC Numero de Cartao de Cidadao
     * @param validade Validade do Cartao de Cidadao
     * @param departamento Departamento em que a pessoa está registada
     */
    protected Pessoa(String nome, String numero, String telemovel, String morada, String password, Departamento departamento, String numeroCC, String validade) {
        Nome = nome;
        NumeroUC = numero;
        Telemovel = telemovel;
        Morada = morada;
        Password = password;
        Departamento = departamento;
        NumeroCC = numeroCC;
        Validade = validade;
    }

    /**
     *Método que permite obter o nome da Pessoa.
     * @return Nome da Pessoa.
     */
    public String getNome() {
        return Nome;
    }

    /**
     *Método que permite obter o número da UC.
     * @return Numero da UC.
     */
    public String getNumeroUC() {
        return NumeroUC;
    }

    /**
     *Método que permite obter o numero de telemovel.
     * @return Numero de telemovel.
     */
    public String getTelemovel() {
        return Telemovel;
    }

    /**
     *Método que permite obter a morada.
     * @return Morada da pessoa.
     */
    public String getMorada() {
        return Morada;
    }

    /**
     *Método que permite obter a password do utilizador.
     * @return Password do utilizador.
     */
    public String getPassword() {
        return Password;
    }

    /**
     *Método que permite obter o departamento onde a pessoa está registada.
     * @return Departamento onde a pessoa está registada.
     */
    public Departamento getDepartamento() {
        return Departamento;
    }

    /**
     *Método que permite obter o numero do cartao de utilizador.
     * @return Password do utilizador.
     */
    public String getNumeroCC() {
        return NumeroCC;
    }

    /**
     *Método que permite obter a validade do cartao de cidadao do utilizador.
     * @return Validade do cartao de cidadao.
     */
    public String getValidade() {
        return Validade;
    }

    /**
     *Método abstrato que permite inserir listas de pessoas em departamentos
     *@param lista lista de pessoas a inserir
     *@param dep departamento onde queremos registar as pessoas
     */
    public abstract void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep);

    /**
     *Método abstrato que permite inserir listas de pessoas em departamentos
     *@param dg Eeição onde queremos inserir o voto.
     *@param voto voto a ser inserido.
     */
    public abstract void addVotoGeral(DirecaoGeral dg, Voto voto);


    /**
     *Método abstrato que permite retirar um eleitor da lista de eleitores quando o mesmo já votou.
     *@param dg Eeição onde queremos retirar o eleitor.
     *@param voto voto do eleitor.
     */
    public abstract void removerEleitorGeral(DirecaoGeral dg, Voto voto);


    /**
     *Método abstrato que permite inserir um eleitor na lista correspondente numa eleição da direção geral.
     *@param dg Eeição onde queremos inserir o eleitor.
     */
    public abstract void AddEleitorGeral(DirecaoGeral dg);

    /**
     *Método abstrato que permite obter as listas candidatas de uma eleição da direção geral.
     *@return  Listas Candidatas da eleição.
     */
    public abstract ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg);

    public void print(){
    }

}
