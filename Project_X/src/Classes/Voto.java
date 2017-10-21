package Classes;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Voto {
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

    Pessoa getEleitor() {
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
