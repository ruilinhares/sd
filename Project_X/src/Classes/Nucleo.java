package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.text.*;

/**
 *Classe derivada da classe ELeicao, representa uma eleição para um núcleo de estudantes de um departamento.
 */
public class Nucleo extends Eleicao implements Serializable {
    /**
     *Atributo que representa o departamento onde decorre a eleição.
     */
    private Departamento departamento;
    /**
     *Atributo que representa a lista de eleitores desta eleição.
     */
    private ArrayList<Pessoa> listaEleitores;
    /**
     *Atributo que representa a lista de votos dos eleitores.
     */
    private ArrayList<Voto> listaVotos;
    /**
     *Atributo que representa a lista de listas candidatas da eleição.
     */
    private ArrayList<ListaCandidata> listaCandidatos;

    /**
     *Construtor da classe.
     * @param titulo Titulo da eleicao
     * @param descricao Descricao da eleicao
     * @param inicio Data e hora do inicio da eleicao
     * @param fim Data e hora do fim da eleicao
     * @param departamento Departamento onde decorre a eleicao
     * @param listaCandidatos Lista de candidatos da eleição
     */
    public Nucleo(String titulo, String descricao, Calendar inicio, Calendar fim, Departamento departamento, ArrayList<ListaCandidata> listaCandidatos) {
        super(titulo, descricao, inicio, fim);
        this.departamento = departamento;
        this.listaCandidatos = listaCandidatos;
        this.listaVotos = new ArrayList<>();
        this.listaEleitores = new ArrayList<>(departamento.getListaEstudantes());
        this.listaCandidatos.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatos.add(new ListaCandidata("Voto em Branco"));
    }


    /**
     *Metodo que permite remover um eleitor da lista após este votar
     *@param voto voto do eleitor
     */
    @Override
    public void removeEleitor(Voto voto) {
        for (Pessoa p : listaEleitores)
            if (p.getNumeroCC().equals(voto.getEleitor().getNumeroCC())) {
                this.listaEleitores.remove(p);
                return;
            }
    }

    /**
     * Metodo que permiteadicionar um voto a lista de votos
     * @param voto
     */
    @Override
    public void addVoto(Voto voto) {
        this.listaVotos.add(voto);
        for (Pessoa p:listaEleitores)
            if (voto.getEleitor().getNumeroCC().equals(p.getNumeroCC())){
                listaEleitores.remove(p);
            }
    }

    /**
     * Metodo que permite obter a lista de eleitores desta eleicao
     * @return Lista de eleitores desta eleicao
     */
    @Override
    public ArrayList<Pessoa> getListaEleitores(){
        return listaEleitores;
    }


    /**
     * Metodo que permite obter a lista de candidatos da eleicao.
     * @return lista de candidatos da eleicao
     */
    @Override
    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa) {
        return listaCandidatos;
    }

    /**
     * Metodo que permite obter editar e gerir os candidatos a esta eleicao
     */
    @Override
    public void EditaCandidatos(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals("1")){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatos.add(nova);
        }
        else if (op.equals("2")){
            if(this.listaCandidatos.isEmpty())
                return;
            System.out.println("Escolha uma lista a remover");
            int i=0;
            for (ListaCandidata dep: this.listaCandidatos){
                i++;
                System.out.println(i+" - "+dep.getNome());
            }
            String opcao;
            opcao=sc.nextLine();
            int opcaoint;
            do{
                try {
                    opcaoint= Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint= 0;
                }
            }while(opcaoint<=0 || opcaoint>this.listaCandidatos.size());
            this.listaCandidatos.remove(opcaoint-1);
        }
    }

    /**
     * Metodo que permite imprimir os dados desta eleicao
     */
    @Override
    public void Print() {
        System.out.println(Titulo);
        System.out.println(Descricao);
        Date Datainicio=inicio.getTime();
        Date Datafim=fim.getTime();
        SimpleDateFormat fim = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Inicio: "+fim.format(Datainicio));
        System.out.println("Fim: "+fim.format(Datafim));
        for (ListaCandidata lista: listaCandidatos){
            System.out.println(lista.getNome());
            int conta=0;
            for (Voto voto: listaVotos){
                if (voto.getTipo().getNome().equals(lista.getNome()))
                    conta++;
            }
            System.out.println("Número de votos: "+conta);
        }
    }

    /**
     * Metodo que permite saber o numero de votos de uma eleicao
     */
    @Override
    public void numeroVotosAtual() {
        System.out.println("Numero de votos(em tempo real): "+ this.listaVotos.size());
    }

    /**
     * Metodo que permite saber em que local é que um eleitor votou.
     * @param uc numero da uc do eleitor em questão.
     */
    @Override
    public void localVoto(String uc) {
        System.out.println(this.getTitulo());
        for (Voto aux : listaVotos)
            if (uc.equals(aux.getEleitor().getNumeroUC())) {
                System.out.println("Nome: "+aux.getEleitor().getNome());
                System.out.println("Local: "+aux.getLocal().getNome());
                System.out.println("Hora: "+aux.getHoraDeVoto());
            }
    }

    /**
     * Metodo tostring
     * @return string com dados da eleicao
     */
    @Override
    public String toString() {
        return "Nucleo{" +
                "departamento=" + departamento +
                ", listaEleitores=" + listaEleitores +
                ", listaVotos=" + listaVotos +
                ", listaCandidatos=" + listaCandidatos +
                ", Titulo='" + Titulo + '\'' +
                ", Descrição='" + Descricao + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}
