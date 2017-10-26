package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    public DirecaoGeral() {
    }

    public DirecaoGeral(String titulo, String descricao, Calendar inicio, Calendar fim, ArrayList<ListaCandidata> listaCandidatosEstudantes, ArrayList<ListaCandidata> listaCandidatosDocentes, ArrayList<ListaCandidata> listaCandidatosFuncionarios, ArrayList<Pessoa> listaEleitores) {
        super(titulo, descricao, inicio, fim);
        this.listaCandidatosEstudantes = listaCandidatosEstudantes;
        this.listaCandidatosDocentes = listaCandidatosDocentes;
        this.listaCandidatosFuncionarios = listaCandidatosFuncionarios;
        this.listaEstudantes = new ArrayList<>();
        this.listaDocentes = new ArrayList<>();
        this.listaFuncionarios = new ArrayList<>();
        for (Pessoa p : listaEleitores)
            p.AddEleitorGeral(this);
        this.listaVotosEstudantes = new ArrayList<>();
        this.listaVotosDocentes = new ArrayList<>();
        this.listaVotosFuncionarios = new ArrayList<>();
        this.listaCandidatosEstudantes.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosEstudantes.add(new ListaCandidata("Voto em Branco"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosDocentes.add(new ListaCandidata("Voto em Branco"));
        this.listaCandidatosFuncionarios.add(new ListaCandidata("Voto Nulo"));
        this.listaCandidatosFuncionarios.add(new ListaCandidata("Voto em Branco"));
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
        for (Pessoa p : listaEstudantes)
            if (p.getNumeroCC().equals(voto.getEleitor().getNumeroCC())) {
                this.listaEstudantes.remove(p);
                break;
            }
    }

    void removeEleitorDocente(Voto voto) {
        for (Pessoa p : listaDocentes)
            if (p.getNumeroCC().equals(voto.getEleitor().getNumeroCC())) {
                this.listaDocentes.remove(p);
                break;
            }
    }

    void removeEleitorFuncionario(Voto voto) {
        for (Pessoa p : listaFuncionarios)
            if (p.getNumeroCC().equals(voto.getEleitor().getNumeroCC())) {
                this.listaFuncionarios.remove(p);
                break;
            }
    }

    void AddEstudante(Estudante p){
        this.listaEstudantes.add(p);
    }

    void AddDocente(Docente p){
        this.listaDocentes.add(p);
    }

    void AddFuncionario(Funcionario p){
        this.listaFuncionarios.add(p);
    }

    ArrayList<ListaCandidata> getListaCandidatosEstudantes() {
        return listaCandidatosEstudantes;
    }

    ArrayList<ListaCandidata> getListaCandidatosDocentes() {
        return listaCandidatosDocentes;
    }

    ArrayList<ListaCandidata> getListaCandidatosFuncionarios() {
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

    private void EditaCandidatosFuncionarios(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals("1")){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosFuncionarios.add(nova);
        }
        else if (op.equals("2")){
            if(this.listaCandidatosFuncionarios.isEmpty())
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

    private void EditaCandidatosEstudantes(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals("1")){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosEstudantes.add(nova);
        }
        else if (op.equals("2")){
            if(this.listaCandidatosEstudantes.isEmpty())
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

    private void EditaCandidatosDocentes(){
        System.out.print("[1]Adicionar Lista\n[2]Remover Lista");
        Scanner sc= new Scanner(System.in);
        String op=sc.nextLine();
        if (op.equals("1")){
            System.out.println("Introduza um nome para a lista");
            String nome = sc.nextLine();
            ListaCandidata nova = new ListaCandidata(nome);
            this.listaCandidatosDocentes.add(nova);
        }
        else if (op.equals("2")){
            if(this.listaCandidatosDocentes.isEmpty())
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

    @Override
    public void EditaCandidatos(){
        System.out.println("Listas Candidatas de : \n1 - Funiconários\n2 - Docentes\n3 - Estudantes");
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

    @Override
    public void Print() {
        System.out.println(Titulo);
        System.out.println(Descricao);
        Date Datainicio=inicio.getTime();
        Date Datafim=fim.getTime();
        SimpleDateFormat fim = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Inicio: "+fim.format(Datainicio));
        System.out.println("Fim: "+fim.format(Datafim));
        System.out.println("FUNCIONÁRIOS");
        for (ListaCandidata lista: listaCandidatosFuncionarios){
            System.out.println(lista.getNome());
            int conta=0;
            for (Voto voto: listaVotosFuncionarios){
                if (voto.getTipo().getNome().equals(lista.getNome()))
                    conta++;
            }
            System.out.println("Número de votos: "+conta);
        }
        System.out.println("DOCENTES");
        for (ListaCandidata lista: listaCandidatosDocentes){
            System.out.println(lista.getNome());
            int conta=0;
            for (Voto voto: listaVotosDocentes){
                if (voto.getTipo().getNome().equals(lista.getNome()))
                    conta++;
            }
            System.out.println("Número de votos: "+conta);
        }
        System.out.println("ESTUDANTES");
        for (ListaCandidata lista: listaCandidatosEstudantes){
            System.out.println(lista.getNome());
            int conta=0;
            for (Voto voto: listaVotosEstudantes){
                if (voto.getTipo().getNome().equals(lista.getNome()))
                    conta++;
            }
            System.out.println("Número de votos: "+conta);
        }
    }

    @Override
    public void numeroVotosAtual() {
        System.out.println("(Estudantes)Numero de votos(em tempo real): " + this.listaVotosEstudantes.size());
        System.out.println("(Docentes)Numero de votos(em tempo real): " + this.listaVotosDocentes.size());
        System.out.println("(Funcionarios)Numero de votos(em tempo real): " + this.listaVotosFuncionarios.size());
    }

    @Override
    public void localVoto(String uc) {
        System.out.println(this.getTitulo());
        int v = 0;
        for (Voto aux : listaVotosEstudantes)
            if (uc.equals(aux.getEleitor().getNumeroUC())) {
                System.out.print("(Estudantes)");
                System.out.println("Nome: "+aux.getEleitor().getNome());
                System.out.println("Local: "+aux.getLocal().getNome());
                System.out.println("Hora: "+aux.getHoraDeVoto());
                v=1;
            }
        if (v == 0)
            for (Voto aux : listaVotosDocentes)
                if (uc.equals(aux.getEleitor().getNumeroUC())) {
                    System.out.print("(Docentes)");
                    System.out.println("Nome: "+aux.getEleitor().getNome());
                    System.out.println("Local: "+aux.getLocal().getNome());
                    System.out.println("Hora: "+aux.getHoraDeVoto());
                    v=1;
                }
        if (v==0)
            for (Voto aux : listaVotosFuncionarios)
                if (uc.equals(aux.getEleitor().getNumeroUC())) {
                    System.out.print("(Funcionarios)");
                    System.out.println("Nome: "+aux.getEleitor().getNome());
                    System.out.println("Local: "+aux.getLocal().getNome());
                    System.out.println("Hora: "+aux.getHoraDeVoto());
                }
    }
}