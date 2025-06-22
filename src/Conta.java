import java.util.ArrayList;

public abstract class Conta {
    protected int numero;
    protected String nome;
    protected String cpf;
    protected ArrayList<Operacao> operacoes;

    public Conta(int numero, String nome, String cpf) {
        this.numero = numero;
        this.nome = nome;
        this.cpf = cpf;
        this.operacoes = new ArrayList<>();
    }

    public void depositar(double valor) {
        operacoes.add(new Operacao("Deposito", valor));
    }

    public void sacar(double valor) {
        if (getSaldo() > valor) {
            operacoes.add(new Operacao("Saque", -valor));
            System.out.println("Você sacou R$" + String.format("%.2f", valor));
        }
        else{
            System.out.println("Saldo insuficiente!");
            System.out.println("Você tentou sacar R$" + String.format("%.2f", valor) +
                   ", mas só possui R$" + String.format("%.2f", getSaldo()) + ".");

        } 
        
    }

    public double getSaldo() {
        double saldo = 0;
        for (Operacao op : operacoes) {
            saldo += op.getValor();
        }
        return saldo;
    }

    public void getExtrato() {
        double saldo = 0;

        System.out.println("=========== Extrato da Conta " + numero + " ===========");
        System.out.printf("%-12s %-12s %12s\n", "Data", "Operação", "Valor (R$)");
        

        for (Operacao op : operacoes) {
            saldo += op.getValor();
            System.out.println(op); 
        }
        System.out.println("------------------------------------------");
        System.out.printf("Saldo: R$%.2f", saldo);
        System.out.println("\n==========================================");
    }


    public int getNumero() {
        return numero;
    }

    public String getCpf() {
        return cpf;
    }
    @Override
    public abstract String toString();

}
