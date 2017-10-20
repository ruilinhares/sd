import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.text.*;

public class ConsolaAdmin {
    public ConsolaAdmin() {
        super();
    }

    private static int dataValida(int dd, int mm, int yy, int hora, int min) {
        int bissextile;
        if (yy%4==0)
            bissextile=1;
        else
            bissextile=0;
        if (yy==-1) {
            System.out.println("Data Invalida. Tente outra vez\n--------------\n\n");
            return 0;
        }

        if(dd > 31 || dd < 1 || mm > 12 || mm < 1) {
            System.out.println("Data Invalida. Tente outra vez\n--------------\n\n");
            return 0;
        }
        else if(mm == 2 && dd < 30 && bissextile==1 && (hora >= 0 && hora <= 23) && (min >= 0 && min <= 59) || mm == 2 && dd < 29 && bissextile==1) {
            return 1;
        } else if((mm == 4 || mm == 6 || mm == 9 || mm == 11) && dd < 31 && (hora >= 0 && hora <= 23) && (min >= 0 && min <= 59)) {
            return 1;
        } else if(dd < 31 && mm != 2 && (hora >= 0 && hora <= 23) && (min >= 0 && min <= 59)) {
            return 1;
        } else {
            System.out.println("Data Invalida. Tente outra vez\n--------------\n\n");
            return 0;
        }
    }

    private static Calendar pedeData(){
        int conta=0;
        int dia,ano,mes,hora,min;
        String pdia,pano,pmes,phora,pmin;
        do{
            conta=0;
            System.out.printf("Dia:\n");
            Scanner sc = new Scanner(System.in);
            pdia=sc.nextLine();
            if (verificaNum(pdia)==1){
                dia=Integer.parseInt(pdia);
                conta++;
            }
            else
                dia=-1;
            System.out.printf("Mes:\n");
            sc = new Scanner(System.in);
            pmes= sc.nextLine();
            if (verificaNum(pmes)==1){
                mes=Integer.parseInt(pmes);
                conta++;
            }
            else
                mes=-1;
            System.out.printf("Ano:\n");
            sc = new Scanner(System.in);
            pano= sc.nextLine();
            if (verificaNum(pano)==1){
                ano=Integer.parseInt(pano);
                conta++;
            }
            else
                ano=-1;
            System.out.printf("Hora:\n");
            sc = new Scanner(System.in);
            phora= sc.nextLine();
            if (verificaNum(phora)==1){
                hora=Integer.parseInt(phora);
                conta++;
            }
            else
                hora=-1;
            System.out.printf("Minutos:\n");
            sc = new Scanner(System.in);
            pmin= sc.nextLine();
            if (verificaNum(pmin)==1){
                min=Integer.parseInt(pmin);
                conta++;
            }
            else
                min=-1;
        }while(dataValida(dia,mes,ano,hora,min)!=1 || conta!=5);
        Calendar data = new GregorianCalendar();
        data.clear();
        data.set(ano,mes-1,dia,hora,min);
        return data;
    }

    private static int verificaNum(String num){

        int tamanho=num.length();
        if (tamanho==0)
            return 0;
        char c;
        int conta=0;
        for(int i=0;i<tamanho;i++){
            c=num.charAt(i);
            if (Character.isDigit(c))
                conta++;
        }
        if (conta==tamanho)
            return 1;
        else
            return 0;
    }


    // Ponto 1 - REGITAR PESSOAS
    public void registarPessoa(AdminRMIimplements rmi){
        String Nome;
        String NumeroUC; //nº de UC
        String Telemovel;
        String Morada;
        String Password;
        Departamento Departamento;
        String NumeroCC;
        String Validade;

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        try {
            Pessoa novapessoa;
            System.out.println("Numero da UC: ");
            NumeroUC = reader.readLine();
            System.out.println("Nome: ");
            Nome = reader.readLine();
            System.out.println("Numero do Cartao de Cidadao: ");
            NumeroCC = reader.readLine();
            System.out.println("Validade do Cartao de Cidadao: ");
            Validade = reader.readLine();
            System.out.println("Password: ");
            Password = reader.readLine();
            System.out.println("Telemovel: ");
            Telemovel = reader.readLine();
            System.out.println("Morada: ");
            Morada = reader.readLine();
            System.out.println("Departamento: ");
            Departamento = PrintDepartamentos(rmi);

            System.out.println("Registar nova Pessoa!\n\n[1]Estudante [2]Docente [3]Funcionario\n-> ");
            if (reader.readLine().equals("1"))
                novapessoa = new Estudante(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else if (reader.readLine().equals("2"))
                novapessoa = new Docente(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else if (reader.readLine().equals("3"))
                novapessoa = new Funcionario(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else
                novapessoa = null;
            rmi.registarPessoa(novapessoa);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Departamento PrintDepartamentos(AdminRMIimplements rmi){
        try {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            System.out.println("Lista de departamentos existentes:");
            ArrayList<Departamento> listaDep = rmi.getListaDepartamentos();
            int i=1;
            for (Departamento dep: listaDep){
                System.out.println(i+" - "+dep.getNome());
                i++;
            }
            Scanner sc = new Scanner(System.in);
            String opcao;
            opcao=sc.nextLine();
            int opcaoint;
            do{
                try {
                    opcaoint= Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint= 0;
                }
            }while(opcaoint<=0 || opcaoint>listaDep.size());
            return listaDep.get(opcaoint-1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // CRIAR DEPARTAMENTO
    private void criaDepartamento(AdminRMIimplements rmi, ArrayList<Departamento> departamentos){        try {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            System.out.println("Nome do Departamento: ");
            String nomeDep = reader.readLine();
            for (Departamento auxDep : departamentos)
                if (nomeDep.equals(auxDep.getNome())){
                    System.out.println("Departamento já existente!");
                    return ;
                }
            Departamento dep = new Departamento(nomeDep,new ArrayList<>());
            rmi.addDepartamento(dep);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ALETERAR DEPARAMENTO (NOME OU LISTA DE ESTUDANTES)
    private void alterarDepartamento(ArrayList<Departamento> departamentos) {

        try {
            Departamento departamento;
            Scanner scanner = new Scanner(System.in);
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            int count = 1;
            for (Departamento auxDep : departamentos){
                System.out.printf("[%d] %s\n",count,auxDep.getNome());
                count++;
            }
            System.out.println("\nEscolha o Departamenta a alterar\n->");
            departamento = departamentos.get(scanner.nextInt()-1);
            System.out.println("[1] Alterar Nome [2]Alterar Lista de Estudantes\n");
            int opcao = scanner.nextInt();
            if (opcao==1){
                System.out.println("Novo nome do Departamento:");
                departamento.setNome(reader.readLine());
                System.out.println("\n\t*Nome alterado com sucesso*\n");
            }
            else if (opcao==2){
                //gerir lista de estudantes
            }
            else
                System.out.println("\n\t*Opcao Invalida*");


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // APAGAR DEPARAMENTO
    private void apagarDepartamento(ArrayList<Departamento> departamentos) {
        try {
            Scanner scanner = new Scanner(System.in);
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            int count = 1;
            for (Departamento auxDep : departamentos){
                System.out.printf("[%d] %s\n",count,auxDep.getNome());
                count++;
            }
            System.out.println("\nEscolha o Departamenta a apagar\n->");
            departamentos.remove(scanner.nextInt()-1);
            System.out.println("\n\t*Departamento apagado com sucesso*\n");


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Ponto 2 - GERIR DEPARTAMENTOS;
    public  void gerirDepartamentos(AdminRMIimplements rmi) {
        try {
            rmi.sayHello();
            ArrayList<Departamento> departamentos = rmi.getListaDepartamentos();
            System.out.println(departamentos);
            Scanner scanner = new Scanner(System.in);
            System.out.print("[1]Criar Departamento [2]Alterar Departamento [3]Apagar Departamento\n->");
            int opcao = scanner.nextInt();
            if (opcao==1)
                criaDepartamento(rmi, departamentos);
            else if (opcao==2)
                alterarDepartamento(departamentos);
            else if (opcao==3)
                apagarDepartamento(departamentos);
            else
                System.out.println("\n\t*Opcao Invalida*");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Nucleo CriaNucleo(AdminRMIimplements rmi){
        Scanner sc = new Scanner(System.in);
        System.out.println("Indique um título");
        String titulo = sc.nextLine();
        System.out.println("Indique uma descrição");
        String descricao = sc.nextLine();
        System.out.println("Indique uma data de inico das eleições");
        Calendar ini = pedeData();
        System.out.println("Indique uma data de fim das eleições");
        Calendar fim =pedeData();
        Departamento dep = PrintDepartamentos(rmi);
        ArrayList<ListaCandidata> lista = new ArrayList<ListaCandidata>();
        Nucleo election = new Nucleo(titulo,descricao,ini,fim,dep,lista);
        System.out.println(election.toString());
        return election;
    }

    public DirecaoGeral CriaDirecaoGeral(AdminRMIimplements rmi){
        DirecaoGeral election=null;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Indique um título");
            String titulo = sc.nextLine();
            System.out.println("Indique uma descrição");
            String descricao = sc.nextLine();
            System.out.println("Indique uma data de inico das eleições");
            Calendar ini = pedeData();
            System.out.println("Indique uma data de fim das eleições");
            Calendar fim = pedeData();
            ArrayList<ListaCandidata> EstudantesCandidatos = new ArrayList<>();
            ArrayList<ListaCandidata> FuncionariosCandidatos = new ArrayList<>();
            ArrayList<ListaCandidata> DocentesCandidatos = new ArrayList<>();
            ArrayList<Estudante> Students = new ArrayList<>();
            ArrayList<Docente> Doc = new ArrayList<>();
            ArrayList<Funcionario> Func = new ArrayList<>();

            ArrayList<Pessoa> listaP = rmi.getListaPessoas();
            election = new DirecaoGeral(titulo, descricao, ini, fim, EstudantesCandidatos, DocentesCandidatos, FuncionariosCandidatos, Students, Doc, Func);
            for (Pessoa p : listaP) {
                p.AddEleitorGeral(election);
            }
            System.out.println(election.toString());
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
        System.out.println(election.toString());
        return election;
    }

    //Ponto 3 - CRIAR ELEIÇÕES
    public  void CriarEleições(AdminRMIimplements rmi) {
        try {
            System.out.println(rmi.sayHello());
            ArrayList<Departamento> departamentos = rmi.getListaDepartamentos();
            System.out.println("CRIAR ELEIÇÃO\n1.Eleição Núcleo de Estudantes\n2.Eleição da Direção Geral");
            Scanner sc = new Scanner(System.in);
            String opcao;
            int opcaoint;
            do {
                System.out.println("Indique o tipo de eleição");
                opcao = sc.nextLine();
                try {
                    opcaoint = Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint = 0;
                }
            } while (opcaoint!=1 && opcaoint!=2);
            switch(opcaoint) {
                case 1:
                    Eleicao nucleo = CriaNucleo(rmi);
                    rmi.AddEleicao(nucleo);
                    break;
                case 2:
                    Eleicao dg = CriaDirecaoGeral(rmi);
                    rmi.AddEleicao(dg);
                    break;

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public Eleicao EscolheEleicao(AdminRMIimplements rmi){
        try {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            System.out.println("Lista de eleições elegíveis para editar listas:");
            ArrayList<Eleicao> listaEle = rmi.getListaEleicoes();
            int i=1;
            if (listaEle.isEmpty()==true)
                return null;
            for (Eleicao ele: listaEle){
                if (ele.verificaVotação()==false && ele.vericaVotaçãoPassou()==true ){
                    System.out.println(i+" - "+ele.Titulo);
                    i++;
                }
            }
            Scanner sc = new Scanner(System.in);
            String opcao;
            opcao=sc.nextLine();
            int opcaoint;
            do{
                try {
                    opcaoint= Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint= 0;
                }
            }while(opcaoint<=0 || opcaoint>listaEle.size());
            return listaEle.get(opcaoint-1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // Ponto 4 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO
    public void gerirListasCandidatos(AdminRMIimplements rmi) {
        try {
            rmi.sayHello();
            Scanner scanner = new Scanner(System.in);
            Eleicao ele=EscolheEleicao(rmi);
            ele.EditaCandidatos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    // Ponto 6 - ALTERAR PROPRIEDADES DE UMA ELEIÇÃO
    public void ALterarEleicao(AdminRMIimplements rmi) {
        try {
            rmi.sayHello();
            Scanner scanner = new Scanner(System.in);
            Eleicao ele=EscolheEleicao(rmi);
            Scanner sc = new Scanner(System.in);
            System.out.println("Indique um título");
            String titulo = sc.nextLine();
            System.out.println("Indique uma descrição");
            String descricao = sc.nextLine();
            System.out.println("Indique uma data de inico das eleições");
            Calendar ini = pedeData();
            System.out.println("Indique uma data de fim das eleições");
            Calendar fim = pedeData();
            ele.setTitulo(titulo);
            ele.setDescrição(descricao);
            ele.setInicio(ini);
            ele.setFim(fim);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void MenuAdmnin(AdminRMIimplements rmi, ArrayList<Departamento> departamentos) {
        System.out.println("Esolha uma opção:\n1.Registar Pessoas\n2.Gerir Departamentos\n3.Criar Eleição\n4.Gerir Listas Candidatas\n5.Gerir Mesas de Votos\n6.Alterar propriedades de uma eleição");
        Scanner sc = new Scanner(System.in);
        String opcao;
        opcao = sc.nextLine();
        int opcaoint;
        do {
            try {
                opcaoint = Integer.parseInt(opcao);
            } catch (NumberFormatException e) {
                opcaoint = 0;
            }
        } while (opcaoint <= 0 || opcaoint > 6);
        try {
            switch (opcaoint) {
                case 1:
                    registarPessoa(rmi);
                    break;
                case 2:
                    gerirDepartamentos(rmi);
                    break;
                case 3:
                    CriarEleições(rmi);
                    break;
                case 4:
                    gerirListasCandidatos(rmi);
                    break;
                case 5:
                    // Gerir mesas de voto
                    break;
                case 6:
                    ALterarEleicao(rmi);
                    break;
                default:
                    // The user input an unexpected choice.
            }
            MenuAdmnin(rmi,rmi.getListaDepartamentos());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            AdminRMIimplements rmi = (AdminRMIimplements) LocateRegistry.getRegistry(6789).lookup("HelloRMI");
            rmi.sayHello();
            ConsolaAdmin consolaAdmin = new ConsolaAdmin();
            consolaAdmin.MenuAdmnin(rmi,rmi.getListaDepartamentos());

        }catch (Exception e){
            System.out.println("Exception in main ConsolaAdmin: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
