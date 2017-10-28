package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe que representa uma eleição da direção geral
 */
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

    /**
     * Construtor vazio da classe
     */
    public DirecaoGeral() {
    }

    /**
     *Construtor da classe.
     * @param titulo Titulo da eleicao
     * @param descricao Descricao da eleicao
     * @param inicio Data e hora do inicio da eleicao
     * @param fim Data e hora do fim da eleicao
     * @param listaCandidatosDocentes Listas candidatas de docentes
     * @param listaCandidatosEstudantes Listas candidatas de docentes
     * @param listaCandidatosFuncionarios Listas candidatas de funcionários
     * @param listaEleitores Lista de eleitores da eleição
     */
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

    /**
     *Metodo que permite remover um eleitor da lista após este votar
     *@param voto voto do eleitor
     */
    @Override
    public void removeEleitor(Voto voto) {
        voto.getEleitor().removerEleitorGeral(this, voto);
    }

    /**
     * Metodo que permite adicionar um voto a lista de votos
     * @param voto
     */
    @Override
    public void addVoto(Voto voto) {
        voto.getEleitor().addVotoGeral(this,voto);
    }

    /**
     * Método que permite adicionaar um voto
     * @param voto Voto do eleitor
     */
    void addVotoEstudante(Voto voto) {
        this.listaVotosEstudantes.add(voto);
    }

    /**
     * Método que permite adicionar um voto de um docente
     * @param voto Voto do docente
     */
    void addVotoDocente(Voto voto) {
        this.listaVotosDocentes.add(voto);
    }

    /**
     * Método que permite adicionar o voto de um funcionário
     * @param voto Voto de um funcionário
     */
    void addVotoFuncionario(Voto voto) {
        this.listaVotosFuncionarios.add(voto);
    }

    /**
     * Método que permite remover um eleitor após este votar.
     * @param voto Voto do eleitor
     */
    void removeEleitorEstudante(Voto voto) {
        this.listaVotosEstudantes.add(voto);
    }

    /**
     * Método que permite remover um eleitor após este votar.
     * @param voto Voto do eleitor
     */
    void removeEleitorDocente(Voto voto) {
        this.listaVotosDocentes.add(voto);
    }

    /**
     * Método que permite remover um eleitor após este votar.
     * @param voto Voto do eleitor
     */
    void removeEleitorFuncionario(Voto voto) {
        this.listaVotosFuncionarios.add(voto);
    }

    /**
     * Método que permite adicionar um eleitor à lista de eleitores
     * @param p Estudante a adicionar
     */
    void AddEstudante(Estudante p){
        this.listaEstudantes.add(p);
    }

    /**
     * Método que permite adicionar um eleitor à lista de eleitores
     * @param p Docente a adicionar
     */
    void AddDocente(Docente p){
        this.listaDocentes.add(p);
    }

    /**
     * Método que permite adicionar um eleitor à lista de eleitores
     * @param p Funcionário a adicionar
     */
    void AddFuncionario(Funcionario p){
        this.listaFuncionarios.add(p);
    }

    /**
     * Método que permite obter a lista de candidatos estudantes
     * @return lista de candidatos estudantes
     */
    ArrayList<ListaCandidata> getListaCandidatosEstudantes() {
        return listaCandidatosEstudantes;
    }

    /**
     * Método que permite obter a lista de candidatos docentes
     * @return lista de candidatos docentes
     */
    ArrayList<ListaCandidata> getListaCandidatosDocentes() {
        return listaCandidatosDocentes;
    }

    /**
     * Método que permite obter a lista de candidatos funcionários
     * @return lista de candidatos funcionários
     */
    ArrayList<ListaCandidata> getListaCandidatosFuncionarios() {
        return listaCandidatosFuncionarios;
    }

    /**
     * Método que permite obter a lista de eleitores da eleição
     * @return lista de eleitores da eleição
     */
    @Override
    public ArrayList<Pessoa> getListaEleitores(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        lista.addAll(this.listaEstudantes);
        lista.addAll(this.listaDocentes);
        lista.addAll(this.listaFuncionarios);
        return lista;
    }

    /**
     * Método que permite obter a lista de candidatos de uma eleição
     * @param eleitor
     * @return Lista de candidatos
     */
    @Override
    public ArrayList<ListaCandidata> getListaCandidatos(Pessoa eleitor) {
        return eleitor.getListaCandidataGeral(this);
    }

    /**
     * Método que permite editar os candidatos funcionários de uma eleição
     */
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

    /**
     * Método que permite editar os candidatos estudantes de uma eleição
     */
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

    /**
     * Método que permite editar os candidatos docentes de uma eleição
     */
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

    /**
     *
     **/
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