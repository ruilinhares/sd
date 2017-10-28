package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe abstrata que representa uma eleição.
 */
public abstract class Eleicao implements Serializable {
    String Titulo;
    String Descricao;
    Calendar inicio;
    Calendar fim;

    /**
     * Construtor vazio da classe.
     */
    Eleicao(){}

    /**
     *Construtor da classe.
     * @param titulo Titulo da eleicao
     * @param descricao Descricao da eleicao
     * @param inicio Data e hora do inicio da eleicao
     * @param fim Data e hora do fim da eleicao
     */
    Eleicao(String titulo, String descricao, Calendar inicio, Calendar fim) {
        Titulo = titulo;
        Descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    /**
     * Método que permite obter o título da eleição
     * @return Titulo da Eleição
     */
    public String getTitulo() {
        return Titulo;
    }

    /**
     * Método que permite atribuir um título à eleição
     * @param titulo Título que queremos atribuir à eleição
     */
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    /**
     * Método que permite obter a descrição da eleição.
     * @return Descrição da eleição.
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * Método que permite atribuir um título à eleição
     * @param descricao Título que queremos atribuir à eleição
     */
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    /**
     * Método que permite obter a data de início da eleição.
     * @return Data de início da eleição
     */
    public Calendar getInicio() {
        return inicio;
    }

    /**
     * Método que permite atribuir uma data de data de início da eleição.
     * @return Data de início da eleição que pretendemos atribuir
     */
    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    /**
     * Método que permite obter a data de fim da eleição.
     * @return Data de fim da eleição
     */
    public Calendar getFim() {
        return fim;
    }

    /**
     * Método que permite obter a data de fim da eleição.
     * @return Data de fim da eleição
     */
    public void setFim(Calendar fim) {
        this.fim = fim;
    }

    /**
     * Método que permite retirar um eleitor da lista de eleitores após votar.
     * @param voto Voto do eleitor
     */
    public abstract void removeEleitor(Voto voto);

    /**
     * Método que permite adicionar um voto.
     * @param voto Voto a adicionar
     */
    public abstract void addVoto(Voto voto);

    /**
     * Método que permite obter a lista de eleitores de uma eleição.
     * @return Lista de eleitores de uma eleição.
     */
    public abstract ArrayList<Pessoa> getListaEleitores();

    /**
     * Método que pemite obter a lista de candidatos de uma eleição
     * @param pessoa
     * @return Lista de candidatos dessa eleição.
     */
    public abstract ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa);

    /**
     * Método que permite verificar se uma eleição está abera
     * @return True se estiver aberta ou false caso contrário
     */
    public boolean verificaVotacao(){ //verifica se a eleição está aberta ou não
        Calendar DataAtual = Calendar.getInstance();
        System.out.println(DataAtual.after(this.inicio));
        System.out.println(DataAtual.before(this.fim));
        return DataAtual.after(this.inicio) && DataAtual.before(this.fim);
    }

    /**
     * Método que permite verificar se uma eleição já decorreu
     * @return False se estiver aberta e true se estiver fechada
     */
    public boolean vericaVotacaoPassou(){ // verificar se uma eleição já decorreu
        Calendar DataAtual = Calendar.getInstance();
        return !DataAtual.after(this.fim);
    }

    /**
     * Método que permite editar as listas candidatas de uma eleição
     */
    public void EditaCandidatos(){}

    /**
     * Método que permite dar print dos dados da eleição.
     */
    public void Print(){}

    /**
     * Método que permite obter o número de votos atual de uma eleição
     */
    public abstract void numeroVotosAtual();

    /**
     * Método que permite saber o local de voto de um eleitor;
     * @param uc Número do cartão da UC desse eleitor;
     */
    public abstract void localVoto(String uc);

    /**
     * Método que retorna a string com dados da eleição
     * @return String com os dados da eleição
     */
    public String toString() {
        return "Eleicao{" +
                "Titulo='" + Titulo + '\'' +
                ", Descrição='" + Descricao + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }

    public Boolean eleicaoEquals(Eleicao ele){
        return ele.getTitulo().equals(this.Titulo) && ele.getDescricao().equals(this.Descricao) && ele.getInicio().equals(this.inicio) && ele.getFim().equals(this.fim);
    }
}
