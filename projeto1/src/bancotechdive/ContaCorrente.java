package bancotechdive;

public class ContaCorrente extends Conta{
    private double chequeEspecial;

    public ContaCorrente(String nome, String cpf, double rendaMensal, double saldo) {
        super(nome, cpf, rendaMensal, saldo);
        chequeEspecial = rendaMensal * 0.5;
    }
    @Override
    public boolean transferencia(Conta conta, double valor){
        if (conta != null && valor > 0 && getSaldo() - valor >= -chequeEspecial){
            super.transferencia(conta, valor);
            return true;
        }
        return false;
    }
    @Override
    public boolean saque(double valor){
        if (valor > 0 && getSaldo() - valor >= -chequeEspecial) {
            super.saque(valor);
            return true;
        }
        return false;
    }
    public double getChequeEspecial() {
        return chequeEspecial;
    }
    @Override
    public void setRendaMensal(double rendaMensal) {
        super.setRendaMensal(rendaMensal);
        setChequeEspecial(rendaMensal * 0.5);
    }
    public void setChequeEspecial(double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

}
