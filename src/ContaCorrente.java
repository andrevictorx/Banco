public class ContaCorrente extends Conta implements Pix {
    private boolean pixAtivo = false;

    public ContaCorrente(int numero, String nome, String cpf) {
        super(numero, nome, cpf);
    }

    @Override
    public void cadastrarPix(String cpf) {
        Conta conta = Banco.buscarContaPorCpf(cpf);
        if (conta != null && conta instanceof ContaCorrente) {
            if (!Banco.cpfsPix.contains(cpf)) {
                Banco.cpfsPix.add(cpf);
                pixAtivo = true;
                System.out.println("CPF " + cpf + " cadastrado com sucesso no PIX.");
                System.out.println("PIX vinculado à conta:");
                System.out.println(conta);
            } else {
                System.out.println("CPF já está cadastrado no sistema PIX.");
            }
        } else {
            System.out.println("CPF não encontrado ou não pertence a uma conta corrente.");
        }
    }


    @Override
    public void efetuarPix(String destinoCpf, double valor) {
        // Verifica se o PIX está ativo nesta conta
        if (!pixAtivo) {
            System.out.println("Sua conta não está cadastrada no PIX.");
            return;
        }

        // Verifica se o destino está autorizado a receber PIX
        if (!Banco.cpfsPix.contains(destinoCpf)) {
            System.out.println("O CPF de destino não está autorizado no sistema PIX.");
            return;
        }

        // Busca a conta de destino
        Conta destino = Banco.buscarContaPorCpf(destinoCpf);
        if (destino == null || !(destino instanceof ContaCorrente)) {
            System.out.println("Conta de destino não encontrada ou não é uma conta corrente.");
            return;
        }

        // Verifica saldo
        if (this.getSaldo() < valor) {
            System.out.println("Saldo insuficiente para efetuar o PIX.");
            return;
        }

        // Registra saída e entrada
        operacoes.add(new Operacao("PIX Out", -valor));
        ((Pix) destino).receberPix(this.cpf, valor);

        System.out.println("PIX de R$" + String.format("%.2f", valor) + " enviado para CPF " + destinoCpf + " com sucesso.");
    }


    @Override
    public void receberPix(String origemCpf, double valor) {
        operacoes.add(new Operacao("PIX In", valor)); // isso já aumenta o saldo
    }

    @Override
    public String toString() {
        return "\n========== Conta N° " + numero + " =========\n" +
            "Tipo de conta: Corrente\n" +
            "Nome: " + nome + "\n" +
            "CPF: " + cpf + "\n" +
            "PIX cadastrado: " + (pixAtivo ? "sim" : "não");
    }


}
