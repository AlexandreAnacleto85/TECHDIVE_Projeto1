package bancotechdive;

public final class Transferencia extends Operacao{
    public Conta destino;
    public Conta origem;

    public Transferencia(Conta origem, Conta destino, double valor) {
        super(valor,"transferencia");
        this.origem = origem;
        this.destino = destino;
    }

    @Override
    public String toString() {
        return String.format("%n%-23s %-15s %8.02f %s %d -> %s %d",
                DATA, TIPO, VALOR, origem.getNome(), origem.getCONTA(), destino.getNome(), destino.getCONTA());
    }
}
