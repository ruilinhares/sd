package Classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Voto implements Serializable {
    private Pessoa eleitor;
    private ListaCandidata tipoVoto;
    private Calendar horaDeVoto;
    private Departamento local;

    public Voto(Pessoa eleitor, ListaCandidata tipo, Departamento local) {
        this.eleitor = eleitor;
        this.tipoVoto = tipo;
        this.horaDeVoto = new GregorianCalendar();
        this.local = local;
    }

    public Pessoa getEleitor() {
        return eleitor;
    }

    public ListaCandidata getTipo() {
        return tipoVoto;
    }

    public Calendar getHoraDeVoto() {
        return horaDeVoto;
    }

    public Departamento getLocal() {
        return local;
    }
}
