package Classes;

import java.util.*;

public abstract class Eleicao {
    protected String Titulo;
    protected String Descrição;
    protected GregorianCalendar inicio;
    protected GregorianCalendar fim;
    // serveresTCP

    public Eleicao(String titulo, String descrição, GregorianCalendar inicio, GregorianCalendar fim) {
        Titulo = titulo;
        Descrição = descrição;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescrição() {
        return Descrição;
    }

    public void setDescrição(String descrição) {
        Descrição = descrição;
    }

    public GregorianCalendar getInicio() {
        return inicio;
    }

    public void setInicio(GregorianCalendar inicio) {
        this.inicio = inicio;
    }

    public GregorianCalendar getFim() {
        return fim;
    }

    public void setFim(GregorianCalendar fim) {
        this.fim = fim;
    }

    //remover eleitor da lista de eleitores quando for votar para nao poder votar outra vez
    public abstract void removeEleitor(Voto voto);

    //adicionar voto do eleitor
    public abstract void addVoto(Voto voto);

    public abstract ArrayList<Pessoa> getListaEleitores();

    public abstract ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa);
}
