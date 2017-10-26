package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.text.*;

public class Nucleo extends Eleicao implements Serializable {
    private Departamento departamento;
    private ArrayList<Pessoa> listaEleitores;
    private ArrayList<Voto> listaVotos;
    private ArrayList<ListaCandidata> listaCandidatos;

    public Nucleo(String titulo, String descricao, Calendar inicio, Calendar fim, Departamento departamento, ArrayList<ListaCandidata> listaCandidatos) {
        super(titulo, descricao, inicio, fim);
        this.departamento = departamento;
        this.listaCandidatos = listaCandidatos;
        this.listaVotos = new ArrayList<>();
        this.listaEleitores = new ArrayList<>();
        this.listaEleitores.addAll(departamento.getListaEstudantes());
        System.out.println(listaEleitores.size());
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
        return this.listaEleitores;
    }

    @Override
    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa) {
        return listaCandidatos;
    }

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

    @Override
    public void numeroVotosAtual() {
        System.out.println("Numero de votos(em tempo real): "+ this.listaVotos.size());
    }

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
