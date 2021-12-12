package bancotechdive;

import java.util.ArrayList;
import java.math.RoundingMode;
import java.math.BigDecimal;

public abstract class Conta {
    private String nome;
    private final String CPF;
    private final int CONTA;
    private final String AGENCIA;
    private double rendaMensal;
    private double saldo;
    private ArrayList<Operacao> operacoes = new ArrayList<Operacao>();
    public static int qtdContas;
    public static String cabecalhoLista =
            String.format("%n%-20s %-15s %5s %5s %5s %5s %5s" +
                            "%n--------------------------------------------------------------------------------%n",
                    "Tipo","Titular", "Conta", "Agencia", "Saldo", "Renda mensal", "CPF");

    public Conta(String nome, String cpf, double rendaMensal, double saldo) {
        qtdContas++;
        this.nome = nome;
        this.CPF = cpf;
        this.rendaMensal = rendaMensal;
        this.saldo = saldo;
        this.CONTA = qtdContas;
        this.AGENCIA = (int) (Math.random() * 2) == 0 ? "001" : "002";
    }
    public void depositoTransacional(Conta conta, double valor){
        saldo += valor;
        operacoes.add(new Transferencia(this, conta, valor));
    }
    public static double jurosAnualParaMensal(double jurosAnual){
        return BigDecimal.valueOf(Math.pow(1 + jurosAnual/100, 1.0 / 12.0))
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue() - 1;
    }
    public boolean saque(double valor){
        if (valor > 0) {
            saldo -= valor;
            operacoes.add(new Operacao(-valor, "saque"));
            return true;
        }
        return false;
    }
    public void saqueTransacional(Conta conta, double valor){
        saldo -= valor;
        operacoes.add(new Transferencia(this, conta, -valor));
    }
    public boolean deposito(double valor){
        if (valor > 0) {
            saldo += valor;
            operacoes.add(new Operacao(valor, "deposito"));
            return true;
        }
        return false;
    }
    public boolean transferencia(Conta conta, double valor){
        if (conta != null && valor > 0){
            this.saqueTransacional(conta, valor);
            conta.depositoTransacional(this, valor);
            return true;
        }
        return false;
    }
    public void extrato(){
        for (Operacao operacao : operacoes) {
            System.out.println(operacao);
        }
        System.out.printf("%n saldo total: %.02f%n", getSaldo());
    }

    @Override
    public String toString() {
        return String.format("%-20s %-15s %-5s %-5s %8s %10s %12s%n",
                this.getClass().getSimpleName(),
                this.getNome(),
                this.getCONTA(),
                this.getAGENCIA(),
                this.getSaldo(),
                this.getRendaMensal(),
                this.getCPF());
    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getCONTA() {
        return CONTA;
    }

    public String getAGENCIA() {
        return AGENCIA;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRendaMensal(double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }
}
