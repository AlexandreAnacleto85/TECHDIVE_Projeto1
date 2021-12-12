package bancotechdive;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Operacao {
    public final double VALOR;
    public final String DATA;
    public final String TIPO;

    Operacao(double VALOR, String TIPO) {
        this.VALOR = VALOR;
        this.TIPO = TIPO;
        this.DATA = DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "\n" + DATA + " " + TIPO + " "  + VALOR;
    }
}
