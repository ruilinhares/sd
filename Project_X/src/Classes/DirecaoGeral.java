package Classes;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DirecaoGeral extends Eleicao{
    private ArrayList<Voto> listaVotosEstudantes;
    private ArrayList<Voto> listaVotosDocentes;
    private ArrayList<Voto> listaVotosFuncionarios;
    private ArrayList<ListaCandidata> listaCandidatosEstudantes;
    private ArrayList<ListaCandidata> listaCandidatosDocentes;
    private ArrayList<ListaCandidata> listaCandidatosFuncionarios;
    private ArrayList<Estudante> listaEstudantes;
    private ArrayList<Docente> listaDocentes;
    private ArrayList<Funcionario> listaFuncionarios;

    public DirecaoGeral(String titulo, String descrição, GregorianCalendar inicio, GregorianCalendar fim, ArrayList<ListaCandidata> listaCandidatosEstudantes, ArrayList<ListaCandidata> listaCandidatosDocentes, ArrayList<ListaCandidata> listaCandidatosFuncionarios, ArrayList<Estudante> listaEstudantes, ArrayList<Docente> listaDocentes, ArrayList<Funcionario> listaFuncionarios) {
        super(titulo, descrição, inicio, fim);
        this.listaCandidatosEstudantes = listaCandidatosEstudantes;
        this.listaCandidatosDocentes = listaCandidatosDocentes;
        this.listaCandidatosFuncionarios = listaCandidatosFuncionarios;
        this.listaEstudantes = listaEstudantes;
        this.listaDocentes = listaDocentes;
        this.listaFuncionarios = listaFuncionarios;
        this.listaVotosEstudantes = new ArrayList<>();
        this.listaVotosDocentes = new ArrayList<>();
        this.listaVotosFuncionarios = new ArrayList<>();
        this.listaCandidatosEstudantes.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosEstudantes.add(new ListaCandidata("Voto em Branco"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto em Branco"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto em Branco"));
    }

    @Override
    public void removeEleitor(Voto voto) {
        voto.getEleitor().removerEleitorGeral(this, voto);
    }

    @Override
    public void addVoto(Voto voto) {
        voto.getEleitor().addVotoGeral(this,voto);
    }

    void addVotoEstudante(Voto voto) {
        this.listaVotosEstudantes.add(voto);
    }

    void addVotoDocente(Voto voto) {
        this.listaVotosDocentes.add(voto);
    }

    void addVotoFuncionario(Voto voto) {
        this.listaVotosFuncionarios.add(voto);
    }

    void removeEleitorEstudante(Voto voto) {
        this.listaVotosEstudantes.add(voto);
    }

    void removeEleitorDocente(Voto voto) {
        this.listaVotosDocentes.add(voto);
    }

    void removeEleitorFuncionario(Voto voto) {
        this.listaVotosFuncionarios.add(voto);
    }

    public ArrayList<ListaCandidata> getListaCandidatosEstudantes() {
        return listaCandidatosEstudantes;
    }

    public ArrayList<ListaCandidata> getListaCandidatosDocentes() {
        return listaCandidatosDocentes;
    }

    public ArrayList<ListaCandidata> getListaCandidatosFuncionarios() {
        return listaCandidatosFuncionarios;
    }

    @Override
    public ArrayList<Pessoa> getListaEleitores(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        lista.addAll(this.listaEstudantes);
        lista.addAll(this.listaDocentes);
        lista.addAll(this.listaFuncionarios);
        return lista;
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa eleitor) {
        return eleitor.getListaCandidataGeral(this);
    }
}
