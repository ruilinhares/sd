package Classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe que representa o voto.
 */
public class Voto implements Serializable {
    /**
     *Atributo que representa a pessoa que votou.
     */
    private Pessoa eleitor;
    /**
     *Atributo que representa a lista na qual o eleitor votou.
     */
    private ListaCandidata tipoVoto;
    /**
     *Atributo que representa a hora de voto.
     */
    private Calendar horaDeVoto;
    /**
     *Atributo que representa o departamento onde o eleitor votou.
     */
    private Departamento local;

    /**
     *Construtor da classe.
     * @param eleitor Eleitor que votou.
     * @param tipo Lista candidata na qual o eleitor votou.
     * @param local Departamento onde o eleitor votou.
     */
    public Voto(Pessoa eleitor, ListaCandidata tipo, Departamento local) {
        this.eleitor = eleitor;
        this.tipoVoto = tipo;
        this.horaDeVoto = new GregorianCalendar();
        this.local = local;
    }


    /**
     *Método que permite obter o eleitor que votou.
     * @return ELeitor
     */
    public Pessoa getEleitor() {
        return eleitor;
    }

    /**
     *Método que permite obter a lista em que o eleitor votou.
     * @return Lista em que o eleitor votou
     */
    public ListaCandidata getTipo() {
        return tipoVoto;
    }

    /**
     *Método que permite obter a data e hora de voto..
     * @return Data e hora do voto
     */
    public Calendar getHoraDeVoto() {
        return horaDeVoto;
    }

    /**
     *Método que permite obter o local onde eleitor votou.
     * @return Departamento onde o eleitor votou.
     */
    public Departamento getLocal() {
        return local;
    }
}
