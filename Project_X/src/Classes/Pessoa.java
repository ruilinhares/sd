package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pessoa implements Serializable {
    private String Nome;
    private String NumeroUC; //nº de UC
    private String Telemovel;
    private String Morada;
    private String Password;
    private Departamento Departamento;
    private String NumeroCC;
    private String Validade;

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

    public String getNome() {
        return Nome;
    }

    public String getNumeroUC() {
        return NumeroUC;
    }

    public String getTelemovel() {
        return Telemovel;
    }

    public String getMorada() {
        return Morada;
    }

    public String getPassword() {
        return Password;
    }

    public Departamento getDepartamento() {
        return Departamento;
    }

    public String getNumeroCC() {
        return NumeroCC;
    }

    public String getValidade() {
        return Validade;
    }

    public abstract void inserirPessoaNaLista(ArrayList<Pessoa> lista, Departamento dep);

    //adicionar voto na geral
    public abstract void addVotoGeral(DirecaoGeral dg, Voto voto);

    //remover eleitor da lista de eleitores quando para não poder voltar a votar na geral
    public abstract void removerEleitorGeral(DirecaoGeral dg, Voto voto);

    // adicionar eleitor na lista de elitores correspondente na geral
    public abstract void AddEleitorGeral(DirecaoGeral dg);

    public abstract ArrayList<ListaCandidata> getListaCandidataGeral(DirecaoGeral dg);


}
