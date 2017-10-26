package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;


public abstract class Eleicao implements Serializable {
    String Titulo;
    String Descricao;
    Calendar inicio;
    Calendar fim;
    // serveresTCP

    Eleicao(){}

    Eleicao(String titulo, String descricao, Calendar inicio, Calendar fim) {
        Titulo = titulo;
        Descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Calendar getInicio() {
        return inicio;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public Calendar getFim() {
        return fim;
    }

    public void setFim(Calendar fim) {
        this.fim = fim;
    }

    //remover eleitor da lista de eleitores quando for votar para nao poder votar outra vez
    public abstract void removeEleitor(Voto voto);

    //adicionar voto do eleitor
    public abstract void addVoto(Voto voto);

    public abstract ArrayList<Pessoa> getListaEleitores();

    public abstract ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa);

    public boolean verificaVotacao(){ //verifica se a eleição está aberta ou não
        Calendar DataAtual = Calendar.getInstance();
        //Eleição dentro do horário ou ELeição fora do horário
        return DataAtual.after(this.inicio) && DataAtual.before(this.fim);
    }

    public boolean vericaVotacaoPassou(){ // verificar se uma eleição já decorreu
        Calendar DataAtual = Calendar.getInstance();
        return !DataAtual.after(this.fim);
    }

    public void EditaCandidatos(){}

    public void Print(){}

    public abstract void numeroVotosAtual();

    public abstract void localVoto(String uc);

    public String toString() {
        return "Eleicao{" +
                "Titulo='" + Titulo + '\'' +
                ", Descrição='" + Descricao + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}
