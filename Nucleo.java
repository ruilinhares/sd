import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;

public class Nucleo extends Eleicao implements Serializable{
    private Departamento departamento;
    private ArrayList<Pessoa> listaEleitores;
    private ArrayList<Voto> listaVotos;
    private ArrayList<ListaCandidata> listaCandidatos;

    public Nucleo(){}

    public Nucleo(String titulo, String descrição, Calendar inicio, Calendar fim, Departamento departamento, ArrayList<ListaCandidata> listaCandidatos) {
        super(titulo, descrição, inicio, fim);
        this.departamento = departamento;
        this.listaCandidatos = listaCandidatos;
        this.listaVotos = new ArrayList<>();
        this.listaEleitores = new ArrayList<Pessoa>(departamento.getListaEstudantes());
        this.listaCandidatos.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatos.add(new ListaCandidata("Voto em Branco"));
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setListaEleitores(ArrayList<Pessoa> listaEleitores) {
        this.listaEleitores = listaEleitores;
    }

    public void setListaVotos(ArrayList<Voto> listaVotos) {
        this.listaVotos = listaVotos;
    }

    public void setListaCandidatos(ArrayList<ListaCandidata> listaCandidatos) {
        this.listaCandidatos = listaCandidatos;
    }

    public void removeEleitor(Voto voto) {
        this.listaEleitores.remove(voto.getEleitor());
    }

    public void addVoto(Voto voto) {
        this.listaVotos.add(voto);
    }

    public ArrayList<Pessoa> getListaEleitores(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        for (Pessoa aux : this.departamento.getListaEstudantes())
            lista.add(aux);
        return lista;
    }

    @Override
    public void EditaCandidatos(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals('1')){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatos.add(nova);
        }
        else if (op.equals('2')){
            if(this.listaCandidatos.isEmpty()==true)
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
        else
            return;
    }


    @Override
    public String toString() {
        return "Nucleo{" +
                "departamento=" + departamento +
                ", listaEleitores=" + listaEleitores +
                ", listaVotos=" + listaVotos +
                ", listaCandidatos=" + listaCandidatos +
                ", Titulo='" + Titulo + '\'' +
                ", Descrição='" + Descrição + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }

    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa pessoa) {
        return this.listaCandidatos;
    }
}

