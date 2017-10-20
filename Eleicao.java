import java.util.*;

public abstract class Eleicao {
    protected String Titulo;
    protected String Descrição;
    protected Calendar inicio;
    protected Calendar fim;
    public Eleicao(){}

    public Eleicao(String titulo, String descrição, Calendar inicio, Calendar fim) {
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


    public boolean verificaVotação(){ //verifica se a eleição está aberta ou não
        Calendar DataAtual = Calendar.getInstance();
        if (DataAtual.after(this.inicio) && DataAtual.before(this.fim))
            return true; //Eleição dentro do horário
        else
            return false; //ELeição fora do horário
    }

    public boolean vericaVotaçãoPassou(){ // verificar se uma eleição já decorreu
        Calendar DataAtual = Calendar.getInstance();
        if (DataAtual.after(this.fim))
            return false;//eleição já decorreu
        else
            return true;//eleição ainda não acabou
    }


    public void EditaCandidatos(){}

    public String toString() {
        return "Eleicao{" +
                "Titulo='" + Titulo + '\'' +
                ", Descrição='" + Descrição + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}
