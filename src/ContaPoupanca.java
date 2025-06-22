public class ContaPoupanca extends Conta implements Remunerada {
    public ContaPoupanca(int numero, String nome, String cpf) {
        super(numero, nome, cpf);
    }

    @Override
    public void aplicarCorrecao(double taxa) {
        double correcao = getSaldo() * (taxa / 100);
        operacoes.add(new Operacao("Correção", correcao));
    }

    @Override
    public String toString() {
        return "========== Conta N° " + numero + " =========\n\n" +
            "Tipo de conta: Poupança\n" +
            "Nome: " + nome + "\n" +
            "CPF: " + cpf + "\n\n" +
            "==============================================";
    }


}
