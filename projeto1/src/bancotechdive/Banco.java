package bancotechdive;

import java.util.Scanner;
import java.util.ArrayList;
public class Banco {
    private ArrayList<Conta> contas = new ArrayList<Conta>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Banco banco = new Banco();
        int opcao;
        String menu = """
                        
                        0  - Sair
                        1  - Adicionar conta
                        2  - Sacar
                        3  - Depositar
                        4  - Transferencia
                        5  - Simular rendimento
                        6  - Alterar dados
                        7  - Listar contas
                        8  - Listar contas negativas
                        9  - Listar transacoes de um cliente
                        10 - Obter total investido
                        
                        Opcao:""";

        System.out.println("===================================================");
        System.out.println("                 Banco TechDive");
        System.out.println("===================================================");
        do {
            System.out.print(menu);
            opcao = sc.nextInt();
            switch (opcao){
                case 0:
                    break;
                case 1: banco.adicionarConta();
                    break;
                case 2: banco.sacar();
                    break;
                case 3: banco.depositar();
                    break;
                case 4: banco.transferencia();
                    break;
                case 5: banco.simularRendimento();
                    break;
                case 6: banco.alterarDados();
                    break;
                case 7: banco.listarContas();
                    break;
                case 8: banco.listarContasNegativas();
                    break;
                case 9: banco.listarTrasacoes();
                    break;
                case 10: banco.getTotalInvestido();
                    break;
            }
        }while (opcao != 0);
    }

    private void adicionarConta(){
        String menuTipoConta = """
                                  Conta Corrente     =  cc
                                  Conta Poupanca     =  cp
                                  Conta Investimento =  ci
                                  Sair = 0
                                  
                                  Escolha o tipo de conta :""";
        double saldo;
        double rendaMensal;
        String tipo;
        String cpf;
        String nome;

        sc.nextLine();
        do {
            System.out.print(menuTipoConta);
            tipo = sc.nextLine();
        }while (!tipo.equals("cc") && !tipo.equals("cp") && !tipo.equals("ci") && !tipo.equals("0"));

        if (tipo.equals("0")) return;

        do {
            System.out.print("Digite um CPF : ");
            cpf = sc.nextLine();
            cpf = formatarCPF(cpf);
        }while (!validarCPF(cpf));

        System.out.print("Digite o nome: ");
        nome = sc.nextLine();

        System.out.print("Digite o saldo inicial: ");
        saldo = sc.nextDouble();

        System.out.print("Digite a renda mensal: ");
        rendaMensal = sc.nextDouble();

        switch (tipo){
            case "cc" : contas.add(new ContaCorrente(nome, cpf, rendaMensal, saldo));
                break;
            case "cp" : contas.add(new ContaPoupanca(nome, cpf, rendaMensal, saldo));
                break;
            case "ci" : contas.add(new ContaInvestimento(nome, cpf, rendaMensal, saldo));
        }
        System.out.print(Conta.cabecalhoLista + contas.get(Conta.qtdContas -1));
    }

    private void sacar(){
        Conta conta = getConta();

        System.out.print("Digite o valor de saque: ");
        double valor = sc.nextDouble();

        if (conta.saque(valor))
            System.out.println("\nSaque Realizado Com Sucesso");
        else
            System.out.println("\nSaldo Insuficiente");
    }

    private void depositar(){
        Conta conta = getConta();

        System.out.print("Digite o valor de deposito: ");
        double valor = sc.nextDouble();

        if (conta.deposito(valor))
            System.out.println("\nDeposito Realizado Com Sucesso");
        else
            System.out.println("\nDados Invalido");
    }

    private void transferencia(){
        Conta contaOrigem = getConta();

        System.out.print("Digite o numero da conta do Favorecido: ");
        int numeroConta = sc.nextInt();
        Conta contaDestino = getConta(numeroConta);

        System.out.print("Digite o valor da transferencia: ");
        double valor = sc.nextDouble();
        if (contaOrigem.transferencia(contaDestino, valor))
            System.out.println("\nTransferencia Realizada com Sucesso");
        else
            System.out.println("\nSaldo Insuficiente");

    }
    private void simularRendimento(){
        Conta conta = getConta();

        if (conta.getClass() == ContaPoupanca.class){
            System.out.print("Digite a quantidade de meses: ");
            int meses = sc.nextInt();
            System.out.print("Digite o rendimento: ");
            double rendimentoAnual = sc.nextDouble();

            ContaPoupanca temp = (ContaPoupanca) conta;
            temp.simularRendimento(meses, rendimentoAnual);
        }
        else if (conta.getClass() == ContaInvestimento.class){
            ContaInvestimento temp = (ContaInvestimento) conta;
            ContaInvestimento.listarInvestimentos();

            System.out.print("Escolha o investimento: ");
            int investimentoEscolhido = sc.nextInt();
            System.out.print("Digite a quantidade de meses: ");
            int meses = sc.nextInt();
            System.out.print("Digite o valor para simulacao: ");
            double valor = sc.nextDouble();

            temp.simularRendimento(valor, investimentoEscolhido, meses);
            System.out.println("\nDeseja investir?");
            sc.nextLine();
            String resposta = sc.nextLine();
            if (resposta.startsWith("s")){
                if (temp.investir(valor, investimentoEscolhido))
                    System.out.printf("%n%.02f Reais investidos em %s%n", valor, temp.getInvestimento());
                else
                    System.out.println("\nSaldo Insuficiente");
            }
        }
        else System.out.println("\n Opção Inválida");
    }

    private void alterarDados(){
        Conta conta = getConta();

        int opcao;
        String menu = """
                        
                        0 - Sair
                        1 - Alterar nome
                        2 - Alterar renda mensal
                        
                        """;

        do {
            System.out.println(menu);
            opcao = sc.nextInt();
            switch (opcao){
                case 0 :
                    break;
                case 1:
                    System.out.print("nome atual: " + conta.getNome() + "\nnovo nome: ");
                    sc.nextLine();
                    conta.setNome(sc.nextLine());
                    break;
                case 2:
                    System.out.print("renda mensal atual: " + conta.getRendaMensal() + "\nnova renda mensal: ");
                    conta.setRendaMensal(sc.nextDouble());
            }
        }while (opcao != 0);
        System.out.print(Conta.cabecalhoLista + conta);
    }

    private void listarContas(){
        System.out.println("\nListagem de todas as contas");
        System.out.print(Conta.cabecalhoLista);
        contas.forEach(System.out::print);
    }

    private void listarContasNegativas(){
        System.out.println("\nListagem de todas as contas com saldo negativo");
        System.out.print(Conta.cabecalhoLista);
        contas.stream().filter(conta -> conta.getSaldo() < 0)
                .forEach(System.out::print);
    }

    private void listarTrasacoes(){
        Conta conta = getConta();

        System.out.printf("%nListagem de todas as transacoes do cliente %s%n", conta.getNome());
        conta.extrato();
    }

    private void getTotalInvestido(){
        double totalInvestido = 0;
        for (Conta conta : contas){
            if (conta.getClass() == ContaInvestimento.class){
                ContaInvestimento temp = (ContaInvestimento) conta;
                totalInvestido += temp.getValorInvestido();
            }
        }
        System.out.printf("\nO total investido de contas de Investimento e de %.02f Reais.%n", totalInvestido);
    }

    private Conta getConta(int conta){
        return contas.get(conta - 1);
    }

    private Conta getConta(){
        System.out.print("Digite o numero da conta:");
        int numeroConta = sc.nextInt();
        return getConta(numeroConta);
    }

    private boolean validarCPF(String cpf){
        String digitos = formatarCPF(cpf);
        if (digitos.length() != 11 ) return false;

        int soma;
        int verificador;
        for (int digitoVerificador = 9; digitoVerificador <= 10; digitoVerificador++){
            soma = 0;
            for (int i = 0; i < digitoVerificador; i++){
                soma += (digitos.charAt(i) - 48) * (digitoVerificador + 1 - i);
            }
            verificador = 11 - soma % 11 ;
            verificador = verificador == 10 ? 0 : verificador;

            if (verificador != digitos.charAt(digitoVerificador) - 48) return false;
        }
        return true;
    }

    private String formatarCPF(String cpf){
        return cpf.replaceAll("\\D", "");
    }

}
