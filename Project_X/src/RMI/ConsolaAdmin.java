package RMI;

import Classes.*;
import TCP.TCPServer;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;

import static java.lang.System.exit;

public class ConsolaAdmin implements Serializable{
    AdminRMIimplements rmi;
    private ConsolaAdmin(AdminRMIimplements rmi) {
        super();
        this.rmi = rmi;
        MenuAdmnin();
    }
    // validar uma data
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

    // pedir uma data
    private static Calendar pedeData(){
        int conta;
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
        Calendar data = Calendar.getInstance();
        data.clear();
        data.set(ano,mes-1,dia,hora,min);
        return data;
    }

    // verificar se é um numero
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
    private void registarPessoa(){
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
            Departamento = EscolheDepartamento();

            System.out.println("Registar nova Pessoa!\n\n[1]Estudante [2]Docente [3]Funcionario\n-> ");
            String opcao = reader.readLine();
            if (opcao.equals("1"))
                novapessoa = new Estudante(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else if (opcao.equals("2"))
                novapessoa = new Docente(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else if (opcao.equals("3"))
                novapessoa = new Funcionario(Nome,NumeroUC,Telemovel,Morada,Password,Departamento,NumeroCC,Validade);
            else
            return;
            while (true) {
                try {
                    rmi.registarPessoa(novapessoa);
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }

        }catch (Exception e) {
            System.out.println("Error\n"+e.getMessage());
        }
    }

    //Função que permite escolher um departamento
    private Departamento EscolheDepartamento(){
        try {
            System.out.println("Lista de departamentos existentes:");
            ArrayList<Departamento> listaDep;
            while (true) {
                try {
                    listaDep = rmi.getListaDepartamentos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
            int i=0;
            for (Departamento dep: listaDep){
                i++;
                System.out.println(i+" - "+dep.getNome());
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
    private void criaDepartamento(){
        try {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            System.out.println("Nome do Departamento: ");
            String nomeDep = reader.readLine();
            ArrayList<Departamento> departamentos;
            while (true) {
                try {
                    departamentos = rmi.getListaDepartamentos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
            for (Departamento auxDep : departamentos)
                if ((nomeDep.toUpperCase()).equals(auxDep.getNome().toUpperCase())){
                    System.out.println("Departamento já existente!");
                    return ;
                }
            Departamento dep = new Departamento(nomeDep,new ArrayList<>());
          	while (true) {
                try {
                    rmi.addDepartamento(dep);
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // APAGAR DEPARAMENTO
    private void apagarDepartamento() {
        try {
            Scanner scanner = new Scanner(System.in);
            int count = 0;
			ArrayList<Departamento> departamentos;
            while (true) {
                try {
                    departamentos = rmi.getListaDepartamentos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
            for (Departamento auxDep : departamentos){                
            	count++;
                System.out.printf("[%d] %s\n",count,auxDep.getNome());

            }
            if (count==0) {
                System.out.println("Não existem departamentos");
                return;
            }
            System.out.println("\nEscolha o Departamenta a apagar\n->");
            int opcao = scanner.nextInt();
            System.out.println("Escolheu o "+opcao);
            rmi.RemoveDepartamento(opcao-1);
            System.out.println("\n\t*Departamento apagado com sucesso*\n");


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Ponto 2 - GERIR DEPARTAMENTOS;
    private void gerirDepartamentos() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("[1]Criar Departamento [3]Apagar Departamento\n->");
            int opcao = scanner.nextInt();
            if (opcao==1)
                criaDepartamento();
            else if (opcao==2)
                apagarDepartamento();
            else
                System.out.println("\n\t*Opcao Invalida*");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

//  Ponto 3 - CRIAR ELEIÇÕES -------------------------------
    private void CriarEleicoes() {
        try {
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
                    Eleicao nucleo = CriaNucleo();
                    while (true) {
                        try {
                            rmi.AddEleicao(nucleo);
                            break;
                        } catch (RemoteException ignored) {
                            reconectarRMI();
                        }
                    }
                    break;
                case 2:
                    Eleicao dg = CriaDirecaoGeral();
                    while (true) {
                        try {
                            rmi.AddEleicao(dg);
                            break;
                        } catch (RemoteException ignored) {
                            reconectarRMI();
                        }
                    }
                    break;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //função que permite escolher uma eleição
    private Eleicao EscolheEleicao(){
        try {
            System.out.println("Lista de eleições elegíveis para editar:");
            ArrayList<Eleicao> listaEle;
            while (true) {
                try {
                    listaEle = rmi.getListaEleicoes();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }            
            int i=0;
            if (listaEle.isEmpty()){
                System.out.println("NÃO HÁ ELEIÇÕES ELEGIVEIS");
                return null;
            }
            for (Eleicao ele: listaEle){
            	if (!ele.verificaVotacao() && ele.vericaVotacaoPassou()){
                    i++;
                    System.out.println(i+" - "+ele.getTitulo());
                }
            }
            if (i==0){
                System.out.println("NÃO HÁ ELEIÇÕES ELEGIVEIS");
                return null;
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

    //Função que cria uma eleição para o núcleo de Estudantes
    private Nucleo CriaNucleo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Indique um título");
        String titulo = sc.nextLine();
        System.out.println("Indique uma descrição");
        String descricao = sc.nextLine();
        System.out.println("Indique uma data de inico das eleições");
        Calendar ini = pedeData();
        System.out.println("Indique uma data de fim das eleições");
        Calendar fim =pedeData();
        Departamento dep = EscolheDepartamento();
        ArrayList<ListaCandidata> lista = new ArrayList<>();
        Nucleo election = new Nucleo(titulo,descricao,ini,fim,dep,lista);
        return election;
    }

    //Função que cria uma eleição para a direção geral
   	private DirecaoGeral CriaDirecaoGeral(){
        DirecaoGeral election;
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

        ArrayList<Pessoa> listaP;
        while (true) {
            try {
                listaP = rmi.getListaPessoas();
                break;
            } catch (RemoteException ignored) {
                reconectarRMI();
            }
 		}
        election = new DirecaoGeral(titulo, descricao, ini, fim, EstudantesCandidatos, DocentesCandidatos, FuncionariosCandidatos, listaP);
        return election;
    }

    //  Ponto 4 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO -------
    private void gerirListasCandidatos() {
        try {
            Eleicao ele=EscolheEleicao();
            ArrayList<Eleicao> eleicoes;
            while (true) {
                try {
                    eleicoes = rmi.getListaEleicoes();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
            for (int i=0;i<eleicoes.size();i++)
                if ((ele.getTitulo().toUpperCase()).equals(rmi.getListaEleicoes().get(i).getTitulo().toUpperCase()) && (ele.getDescricao().toUpperCase()).equals(rmi.getListaEleicoes().get(i).getDescricao().toUpperCase()))
                    rmi.RemoveEleicao(i);

            if (ele != null) {
                ele.EditaCandidatos();
                while (true) {
                    try {
                        rmi.AddEleicao(ele);
                        break;
                    } catch (RemoteException ignored) {
                        reconectarRMI();
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //  Ponto 5 - GERIR MESAS DE VOTO
    private void GerirMesasdeVoto(){
        Scanner sc = new Scanner(System.in);
        String opcao;
        int opcaoint;
        do {
            System.out.println("1 - Criar mesas de voto||||2 - Apagar Mesas de voto||||3 - Associar eleições a mesas de voto");
            opcao = sc.nextLine();
            try {
                opcaoint = Integer.parseInt(opcao);
            } catch (NumberFormatException e) {
                opcaoint = 0;
            }
        } while (opcaoint!=1 && opcaoint!=2 && opcaoint!=3);
        switch (opcaoint){
            case 1:
                CriaMesadeVoto();
                break;
            case 2:
                ApagaMesadeVoto();
                break;
            case 3:
                AssociarMesadeVoto();
                break;
        }
    }

    private void CriaMesadeVoto() {
        try {
            Departamento dep = EscolheDepartamento();
            ArrayList<TCPServer> mesas;
            while (true)
                try {
                    mesas = rmi.getMesasVotos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            for (TCPServer mesa : mesas){
                if((mesa.getDepartamento().getNome().toUpperCase()).equals(dep.getNome().toUpperCase())){
                    System.out.println("Este departamento já tem uma mesa");
                    return;
                }
            }
            TCPServer novamesa=new TCPServer(dep);
            while (true)
                try {
                    rmi.AddMesa(novamesa);
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void ApagaMesadeVoto(){
        try{
        	ArrayList<TCPServer> mesas;
            while (true)
                try {
                    mesas = rmi.getMesasVotos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            if (mesas.isEmpty()){
            	System.out.println("\n*Nao ha mesas de voto*");
                return;
            }
            int i=0;
            for(TCPServer mesa: mesas)
                System.out.println((++i)+" - "+mesa.getDepartamento().getNome());
            String opcao;
            Scanner sc= new Scanner(System.in);
            int opcaoint;
            do {
                System.out.println("Escolha a mesa a apagar");
                opcao = sc.nextLine();
                try {
                    opcaoint = Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint = -1;
                }
            } while (opcaoint<1 && opcaoint> mesas.size());
            TCPServer mesaescolhida = mesas.get(opcaoint-1);
            for(Eleicao election: mesaescolhida.getListaEleicoes()){
                if (election.vericaVotacaoPassou()) {
                    System.out.println("Mesa com eleições. Impossível apagar");
                    return;
                }
            }
            rmi.RemoveMesa(opcaoint-1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Associa uma eleição a uma mesa de voto
    private void AssociarMesadeVoto() {
        try {
            Eleicao ele=EscolheEleicao();
            if (ele==null)
                return;
            int i=0;
            ArrayList<TCPServer> mesas;
            while (true)
                try {
                    mesas = rmi.getMesasVotos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            for(TCPServer mesa: mesas)
                System.out.println((++i)+" - "+mesa.getDepartamento().getNome());
            if (i==0){
                System.out.println("não existem mesas de voto");
                return;
            }
            String opcao;
            Scanner sc= new Scanner(System.in);
            int opcaoint;
            do {
                System.out.println("Escolha a mesa");
                opcao = sc.nextLine();
                try {
                    opcaoint = Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint = -1;
                }
            } while (opcaoint<1 && opcaoint>mesas.size());
            TCPServer mesaescolhida = mesas.get(opcaoint-1);
            for (Eleicao election : mesaescolhida.getListaEleicoes() ){
                if(election.getTitulo().equals(ele.getTitulo())&&election.getDescricao().equals(ele.getDescricao())){
                    System.out.println("Esta eleição já tem uma mesa neste departamento");
                    return;
                }
            }
            while (true){
            	try {
					rmi.RemoveMesa(opcaoint-1);
                    break;
               	} catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
            mesaescolhida.addEleicao(ele);
            while (true) {
                try {
                    rmi.AddMesa(mesaescolhida);
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //  Ponto 6 - ALTERAR PROPRIEDADES DE UMA ELEIÇÃO ------------
    private void AlterarEleicao() {
        try {
            Scanner sc = new Scanner(System.in);
            Eleicao ele=EscolheEleicao();
            if (ele==null)
                return;
            for (int i=0;i<rmi.getListaEleicoes().size();i++){
                if (ele.getTitulo().equals(rmi.getListaEleicoes().get(i).getTitulo()) && ele.getDescricao().equals(rmi.getListaEleicoes().get(i).getDescricao() )){
                    rmi.RemoveEleicao(i);
                }
            }
            System.out.println("Indique um título");
            String titulo = sc.nextLine();
            System.out.println("Indique uma descrição");
            String descricao = sc.nextLine();
            System.out.println("Indique uma data de inico das eleições");
            Calendar ini = pedeData();
            System.out.println("Indique uma data de fim das eleições");
            Calendar fim = pedeData();
            if (ele != null) {
                ele.setTitulo(titulo);
                ele.setDescricao(descricao);
                ele.setInicio(ini);
                ele.setFim(fim);
                while (true) {
                    try {
                        rmi.AddEleicao(ele);
                        break;
                    } catch (RemoteException ignored) {
                        reconectarRMI();
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void stats(AdminRMIimplements rmi){
        try {
            System.out.println("Pessoas");
            for (Pessoa pep : rmi.getListaPessoas()) {
                pep.print();
            }
            System.out.println("Eleições");
            for (Eleicao pep : rmi.getListaEleicoes()) {
                pep.Print();
                for (Pessoa p:pep.getListaEleitores())
                    System.out.println(p.getNumeroUC());
            }
            System.out.println("Departamentos");
            for (Departamento pep : rmi.getListaDepartamentos()) {
                pep.print();
            }
            System.out.println("Mesas");
            for (TCPServer pep :rmi.getMesasVotos()){
                System.out.println(pep.getDepartamento().getNome());
                for (Eleicao e : pep.getListaEleicoes()) {
                    System.out.println(e.getDescricao());
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    //Consola de Admin mostra mesas on/off
    private void MesasOnOff(){
        try {
            ArrayList<TCPServer> mesas;
            while (true)
                try {
                    mesas = rmi.getMesasVotos();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            for (TCPServer mesa : mesas)
                System.out.println(mesa.getDepartamento().getNome() +"("+ mesa.getEstadoMesa()+")");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void EscolheEleicaoPassada(){
        try{
           	ArrayList<Eleicao> listaEle;
            while (true)
                try {
                    listaEle = rmi.getListaEleicoes();
                    break;
                } catch (RemoteException ignored) {
                    reconectarRMI();
                }
            ArrayList<Eleicao> nova = new ArrayList<>();
            int i=0;
            if (listaEle.isEmpty()){
            	System.out.println("\n\t*Nao ha eleicoes*\n");
                return;
            }
            for (Eleicao ele: listaEle){
                if (!ele.vericaVotacaoPassou())
                    nova.add(ele);
            }
            System.out.println("Lista de eleições passadas:");
            for (Eleicao election: nova)
                System.out.println((++i)+" - "+election.getTitulo());

            String opcao;
            if (nova.isEmpty()){
                System.out.println("Não há eleições passadas");
                return;
            }
            Scanner sc= new Scanner(System.in);
            int opcaoint;
            do {
                System.out.println("Escolha a eleição");
                opcao = sc.nextLine();
                try {
                    opcaoint = Integer.parseInt(opcao);
                } catch (NumberFormatException e) {
                    opcaoint = -1;
                }
            } while (opcaoint<1 && opcaoint>nova.size());
            nova.get(opcaoint-1).Print();


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //  Ponto 10 - SABER O LOCAL DE VOTO DE CADA ELEITOR
    private void localVotoEleitor() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\tLocais de voto do eleitor\n");
        System.out.print("Numero da UC: ");
        String numeroUC = reader.readLine();
        ArrayList<Eleicao> eleicoes = new ArrayList<>();
        while (true) {
            try {
                eleicoes = rmi.getListaEleicoes();
                break;
            } catch (RemoteException ignored) {
                reconectarRMI();
            }
        }

        for (Eleicao aux : eleicoes)
            aux.localVoto(numeroUC);
    }

 //  Ponto 12  - CONSULTAR ELEICOES EM TEMPO REAL
    private void consultarEleicoesTempoReal() {
        int i = 0;
        ArrayList<TCPServer> mesasVotos;
        while (true)
            try {
                mesasVotos = rmi.getMesasVotos();
                break;
            } catch (RemoteException ignored) {
                reconectarRMI();
            }

        if (!mesasVotos.isEmpty()) {
            System.out.println("\tMesas de Votos abertas:");
            for (TCPServer mesa : mesasVotos) {
                if (mesa.getEstadoMesa())
                    System.out.println("[" + (++i) + "]" + mesa.getDepartamento().getNome());
            }
            if (i!=0) {
                System.out.print("->");
                Scanner scanner = new Scanner(System.in);
                TCPServer mesaEscolhida = mesasVotos.get(scanner.nextInt() - 1);

                System.out.println("\tEleicoes Ativas: ");
                for (Eleicao eleicao : mesaEscolhida.getListaEleicoes()) {
                    if (eleicao.verificaVotacao()) {
                        System.out.println(eleicao.getTitulo());
                        eleicao.numeroVotosAtual();
                        System.out.println();
                    }
                }
            }else
                System.out.println("\n\t*Nao ha mesas de votos ativas*");
        }else
            System.out.println("\n\t*Nao ha mesas de votos*");
    }


    private void reconectarRMI()   {
        int sleep = 1000;
        while(true) {
            try {
                this.rmi = (AdminRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI");
                this.rmi.sayHello();
                return;
            } catch (NotBoundException | RemoteException | MalformedURLException ignored) {
                try {
                    Thread.sleep(sleep);
                    sleep *= 2;
                    if (sleep > 16000) {
                        System.out.println("\n\t*Avaria no RMI Server*");
                        exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void MenuAdmnin() {
        stats(this.rmi);
        System.out.println("Esolha uma opção:\n1.Registar Pessoas\n2.Gerir Departamentos\n3.Criar Eleição\n4.Gerir Listas Candidatas\n5.Gerir Mesas de Votos\n6.Alterar propriedades de uma eleição\n7.Ver estado das mesas\n8.Analisar eleições passadas\n9.Local de voto de um eleitor\n10.Consular eleicoes(tempo real)");
        System.out.print("->");
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
        } while (opcaoint <= 0 && opcaoint > 10);
        try {
            switch (opcaoint) {
                case 1:
                    registarPessoa();
                    break;
                case 2:
                    gerirDepartamentos();
                    break;
                case 3:
                    CriarEleicoes();
                    break;
                case 4:
                    gerirListasCandidatos();
                    break;
                case 5:
                    GerirMesasdeVoto();
                    break;
                case 6:
                    AlterarEleicao();
                    break;
                case 7:
                    MesasOnOff();
                    break;
                case 8:
                    EscolheEleicaoPassada();
                    break;
                case 9:
                    localVotoEleitor();
                    break;
                case 10:
                    consultarEleicoesTempoReal();
                    break;
                default:
                    // The user input an unexpected choice.
            }
            MenuAdmnin();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            AdminRMIimplements rmi = (AdminRMIimplements) Naming.lookup("rmi://localhost:6789/HelloRMI");
            rmi.sayHello();
            ConsolaAdmin consolaAdmin = new ConsolaAdmin(rmi);

        }catch (Exception e){
            System.out.println("Exception in main ConsolaAdmin: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
