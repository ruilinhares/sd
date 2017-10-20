package Classes;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Nucleo extends Eleicao {
    private Departamento departamento;
    private ArrayList<Pessoa> listaEleitores;
    private ArrayList<Voto> listaVotos;
    private ArrayList<ListaCandidata> listaCandidatos;

    public Nucleo(String titulo, String descricao, GregorianCalendar inicio, GregorianCalendar fim, Departamento departamento, ArrayList<ListaCandidata> listaCandidatos) {
        super(titulo, descricao, inicio, fim);
        this.departamento = departamento;
        this.listaCandidatos = listaCandidatos;
        this.listaVotos = new ArrayList<>();
        this.listaEleitores = new ArrayList<Pessoa>(departamento.getListaEstudantes());
        this.listaCandidatos.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatos.add(new ListaCandidata("Voto em Branco"));
    }


    @Override
    public void removeEleitor(Voto voto) {
        this.listaEleitores.remove(voto.getEleitor());
    }

    @Override
    public void addVoto(Voto voto) {
        this.listaVotos.add(voto);
    }

    @Override
    public ArrayList<Pessoa> getListaEleitores(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        lista.addAll(this.departamento.getListaEstudantes());
        return lista;
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa) {
        return this.listaCandidatos;
    }
}
