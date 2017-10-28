package Classes;

import java.io.Serializable;

/**
 * Classe que representa uma lista candidata
 */
public class ListaCandidata implements Serializable {
    /**
     * Atributo que representa o nome da lista.
     */
    private String nome;

    /**
     * Construntor vazio da classe.
     */
    public ListaCandidata() {
    }

    /**
     * Construtor da Classe
     * @param nome Nome que queremos atribuir à lista
     */
    public ListaCandidata(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo que permite obter o nome da lista candidata
     * @return Nome da lista candidata
     */
    public String getNome() {
        return nome;
    }
}
