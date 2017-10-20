package RMI;

import Classes.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaAdmin {
    public ConsolaAdmin() {
        super();
    }

    // Ponto 1 - REGITAR PESSOAS
    public void registarPessoa(RMIserver rmi){
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
            System.out.println("Error");
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
            return listaDep.get(i-1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // CRIAR DEPARTAMENTO
    private void criaDepartamento(AdminRMIimplements rmi, ArrayList<Departamento> departamentos){
        try {
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

    // Ponto 3 - GERIR LISTAS DE CANDIDATOS A UMA ELEICAO
    public void gerirListasCandidatos(AdminRMIimplements rmi) {

    }

    /*
    public void MenuAdmnin(RMIimplements rmi, ArrayList<Departamento> departamentos){
        System.out.println("Esolha uma opção:\n1.Registar Pessoas\n2.Gerir Departamentos\n");

        switch (choice) {
            case 1:
                // Perform "original number" case.
                break;
            case 2:
                // Perform "encrypt number" case.
                break;
            case 3:
                // Perform "decrypt number" case.
                break;
            case 4:
                // Perform "quit" case.
                break;
            default:
                // The user input an unexpected choice.
        }*/

    public static void main(String args[]) {
        try {
            AdminRMIimplements rmi = (AdminRMIimplements) LocateRegistry.getRegistry(6789).lookup("HelloRMI");
            rmi.sayHello();
            ConsolaAdmin consolaAdmin = new ConsolaAdmin();


        }catch (Exception e){
            System.out.println("Exception in main ConsolaAdmin: " + e.getMessage());
            e.printStackTrace();
        }
    }


}