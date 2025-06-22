import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Operacao {
    private LocalDate data;
    private String tipo;
    private double valor;
    

    public Operacao(String tipo, double valor) {
        
        this.data = LocalDate.now();
        this.tipo = tipo;
        this.valor = valor;
    }
    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%-12s %-12s R$%10.2f",
                            data.format(formatter),
                            tipo,
                            valor);
    }
}
