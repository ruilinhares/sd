package Classes;

import java.io.Serializable;

public class ListaCandidata implements Serializable {
    private String nome;

    public ListaCandidata() {
    }

    public ListaCandidata(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
