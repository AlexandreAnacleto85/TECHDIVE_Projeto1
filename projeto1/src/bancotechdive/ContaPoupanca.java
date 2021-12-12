package bancotechdive;

public class ContaPoupanca extends  Conta{
        public ContaPoupanca(String nome, String cpf, double rendaMensal, double saldo) {
            super(nome, cpf, rendaMensal, saldo);
        }

        public void simularRendimento(int meses, double porcentagemAnual){
            double porcentagemMensal = jurosAnualParaMensal(porcentagemAnual);
            double total = getSaldo();

            for (int i = 1; i <= meses; i++){
                total += total * porcentagemMensal + getRendaMensal();
            }
            System.out.printf("%n %d meses %.02f Reais da poupanca com rendimento anual de %.01f%% vao se transformar em %.02f Reais %n",
                    meses, getSaldo(), porcentagemAnual, total);
        }

    @Override
    public boolean transferencia(Conta conta, double valor) {
        if (conta != null && valor > 0 && valor <= getSaldo()) {
            super.transferencia(conta, valor);
            return true;
        }
        return false;

    }
        @Override
        public boolean saque(double valor){
            if (valor > 0 && valor <= getSaldo()) {
                super.saque(valor);
                return true;
            }
            return false;
        }


}