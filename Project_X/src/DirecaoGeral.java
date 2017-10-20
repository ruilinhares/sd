import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.*;

public class DirecaoGeral extends Eleicao implements Serializable{
    private ArrayList<Voto> listaVotosEstudantes;
    private ArrayList<Voto> listaVotosDocentes;
    private ArrayList<Voto> listaVotosFuncionarios;
    private ArrayList<ListaCandidata> listaCandidatosEstudantes;
    private ArrayList<ListaCandidata> listaCandidatosDocentes;
    private ArrayList<ListaCandidata> listaCandidatosFuncionarios;
    private ArrayList<Estudante> listaEstudantes;
    private ArrayList<Docente> listaDocentes;
    private ArrayList<Funcionario> listaFuncionarios;

    public DirecaoGeral(){}
    public DirecaoGeral(String titulo, String descrição, Calendar inicio, Calendar fim, ArrayList<ListaCandidata> listaCandidatosEstudantes, ArrayList<ListaCandidata> listaCandidatosDocentes, ArrayList<ListaCandidata> listaCandidatosFuncionarios, ArrayList<Estudante> listaEstudantes, ArrayList<Docente> listaDocentes, ArrayList<Funcionario> listaFuncionarios) {
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

    public void removeEleitor(Voto voto) {
        voto.getEleitor().removerEleitorGeral(this, voto);
    }

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

    public void AddEstudante(Estudante p){
        this.listaEstudantes.add(p);
    }

    public void AddDocente(Docente p){
        this.listaDocentes.add(p);
    }

    public void AddFuncionario(Funcionario p){
        this.listaFuncionarios.add(p);
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

    public ArrayList<Pessoa> getListaEleitores(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        lista.addAll(this.listaEstudantes);
        lista.addAll(this.listaDocentes);
        lista.addAll(this.listaFuncionarios);
        return lista;
    }

    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa eleitor) {
        return eleitor.getListaCandidataGeral(this);
    }

    public void EditaCandidatosFuncionarios(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals('1')){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosFuncionarios.add(nova);
        }
        else if (op.equals('2')){
            if(this.listaCandidatosFuncionarios.isEmpty()==true)
                return;
            System.out.println("Escolha uma lista a remover");
            int i=1;
            for (ListaCandidata dep: this.listaCandidatosFuncionarios){
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
            }while(opcaoint<=0 || opcaoint>this.listaCandidatosFuncionarios.size());
            this.listaCandidatosFuncionarios.remove(opcaoint-1);

        }
    }

    public void EditaCandidatosEstudantes(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals('1')){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosEstudantes.add(nova);
        }
        else if (op.equals('2')){
            if(this.listaCandidatosEstudantes.isEmpty()==true)
                return;
            System.out.println("Escolha uma lista a remover");
            int i=1;
            for (ListaCandidata dep: this.listaCandidatosEstudantes){
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
            }while(opcaoint<=0 || opcaoint>this.listaCandidatosEstudantes.size());
            this.listaCandidatosEstudantes.remove(opcaoint-1);

        }
    }
    public void EditaCandidatosDocentes(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals('1')){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosDocentes.add(nova);
        }
        else if (op.equals('2')){
            if(this.listaCandidatosDocentes.isEmpty()==true)
                return;
            System.out.println("Escolha uma lista a remover");
            int i=1;
            for (ListaCandidata dep: this.listaCandidatosDocentes){
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
            }while(opcaoint<=0 || opcaoint>this.listaCandidatosDocentes.size());
            this.listaCandidatosDocentes.remove(opcaoint-1);

        }
    }


    public void EditaCandidatos(){
        System.out.println("Listas Candidatas de : 1 - Funiconários\n2 - Docentes\n3 - Estudantes");
        Scanner sc = new Scanner(System.in);
        int op=sc.nextInt();
        switch (op) {
            case 1:
                EditaCandidatosFuncionarios();
                break;
            case 2:
                EditaCandidatosDocentes();
                break;
            case 3:
                EditaCandidatosEstudantes();
                break;
        }
    }
}
