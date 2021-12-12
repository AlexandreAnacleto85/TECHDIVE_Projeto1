package bancotechdive;

import java.util.ArrayList;

public class ContaInvestimento extends Conta{
    private static class Investimento {
        String nome;
        double rendimentoAnual;
        static ArrayList<Investimento> investimentos = new ArrayList<Investimento>();
        public double simularRendimento(double valor, int meses){
            double porcentagemMensal = jurosAnualParaMensal(rendimentoAnual);
            return valor * Math.pow(1 + porcentagemMensal, meses);
        }
        static {
            new Investimento("Dividendo", 5.5);
            new Investimento("Tesouro direto", 6);
        }
        Investimento(String nome, double rendimentoAnual) {
            this.nome = nome;
            this.rendimentoAnual = rendimentoAnual;
            investimentos.add(this);
        }
    }

    private Investimento investimento;
    private double valorInvestido;
    public ContaInvestimento(String nome, String cpf, double rendaMensal, double saldo) {
        super(nome, cpf, rendaMensal, saldo);
    }
    public void simularRendimento(double valor, int indexInvestimento, int meses){
        Investimento investimento = Investimento.investimentos.get(indexInvestimento -1);

        double rendimento = investimento.simularRendimento(valor, meses);
        System.out.printf("%nApos %d meses %.02f Reais aplicados em %s com rendimento anual de %.01f%% vao se transformar em %.02f Reais%n",
                meses, valor, investimento.nome, investimento.rendimentoAnual, rendimento);
    }
    public boolean investir(double valor, int indexInvestimento){
        if (saque(valor)){                 //saca o valor do saldo
            deposito(getValorInvestido()); //investimento anterior volta para o saldo
            valorInvestido = valor;
            investimento = Investimento.investimentos.get(indexInvestimento -1);
            return true;
        }
        return false;
    }
    public static void listarInvestimentos() {
        System.out.printf("%n%-20s %5s %n-------------------------------------%n",
                "Investimento","Rendimento anual");
        Investimento.investimentos.forEach(investimento -> System.out.printf("%d %-20s %15.01f%%%n",
                Investimento.investimentos.indexOf(investimento) +1,investimento.nome, investimento.rendimentoAnual));
    }

    @Override
    public boolean saque(double valor){
        if (valor > 0 && valor <= getSaldo()) {
            super.saque(valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferencia(Conta conta, double valor){
        if (conta != null && valor > 0 && valor <= getSaldo()){
            super.transferencia(conta, valor);
            return true;
        }
        return false;
    }

    public String getInvestimento() {
        return investimento.nome;
    }

    public void setInvestimento(Investimento investimento) {
        this.investimento = investimento;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valor) {

        this.valorInvestido = valor;
    }
}
