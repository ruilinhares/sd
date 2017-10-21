package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

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
        this.listaEleitores = new ArrayList<>(departamento.getListaEstudantes());
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
            int i=1;
            for (ListaCandidata dep: this.listaCandidatos){
                System.out.println(i+" - "+dep.getNome());
                i++;
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
