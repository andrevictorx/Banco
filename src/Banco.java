import java.util.ArrayList;
import java.util.Scanner;

public class Banco {
    public static ArrayList<Conta> contas = new ArrayList<>();
    public static ArrayList<String> cpfsPix = new ArrayList<>();
    private static int numeroSequencial = 1;

    public static Conta buscarContaPorNumero(int numero) {
        for (Conta c : contas) {
            if (c.getNumero() == numero) return c;
        }
        return null;
    }

    public static Conta buscarContaPorCpf(String cpf) {
        for (Conta c : contas) {
            if (c.getCpf().equals(cpf)) return c;
        }
        return null;
    }
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", ""); // remove pontos e traços

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                soma1 += num * (10 - i);
                soma2 += num * (11 - i);
            }

            int dv1 = (soma1 * 10) % 11;
            dv1 = (dv1 == 10 || dv1 == 11) ? 0 : dv1;

            soma2 += dv1 * 2;
            int dv2 = (soma2 * 10) % 11;
            dv2 = (dv2 == 10 || dv2 == 11) ? 0 : dv2;

            return dv1 == Character.getNumericValue(cpf.charAt(9)) &&
                dv2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;
        do {
            while (true) {
                System.out.println("\n1) Criar Corrente\n2) Criar Poupança\n3) Depositar\n4) Sacar\n5) Aplicar Correção\n6) Cadastrar PIX\n7) Efetuar PIX\n8) Extrato\n0) Sair\n");
                System.out.print("Escolha uma opção: ");
                if (sc.hasNextInt()) {
                    opcao = sc.nextInt();
                    sc.nextLine(); // consome o \n restante
                    break;
                } else {
                    System.out.println("Entrada inválida! Por favor, digite um número do menu.");
                    sc.nextLine(); // limpa a entrada inválida
                }
            }


            switch (opcao) {
                case 1:
                    System.out.println("\n========== CRIAR CONTA CORRENTE =========");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    if (!Banco.validarCPF(cpf)) {
                        System.out.println("==========================");
                        System.out.println("CPF inválido. Tente novamente.");
                    } else {
                        boolean existe = false;
                        for (Conta c : contas) {
                            if (c.getCpf().equals(cpf)) {
                                if (!c.nome.equalsIgnoreCase(nome)) {
                                    System.out.println("==========================");
                                    System.out.println("CPF já está cadastrado com outro nome.");
                                    System.out.println("Nome esperado: " + c.nome);
                                    System.err.println("Tente novamente com o nome correto.");
                                    existe = true;
                                    break;
                                }
                                if (c instanceof ContaCorrente) {
                                    System.out.println("Já existe uma conta CORRENTE com este CPF.");
                                    System.out.println("========== Conta já existente =========");
                                    System.out.println("N° da conta: " + c.getNumero());
                                    System.out.println("Tipo de conta: Corrente");
                                    System.out.println("Nome: " + c.nome);
                                    System.out.println("CPF: " + c.getCpf());
                                    System.out.println("=======================================");
                                    existe = true;
                                    break;
                                }
                            }
                        }
                        if (!existe) {
                            contas.add(new ContaCorrente(numeroSequencial, nome, cpf));
                            System.out.println("========== Conta criada com sucesso! =========");
                            System.out.println("N° da conta: " + numeroSequencial);
                            System.out.println("Tipo de conta: Corrente");
                            System.out.println("Nome: " + nome);
                            System.out.println("CPF: " + cpf);
                            System.out.println("==============================================");
                            numeroSequencial++;
                        }
                    }
                    break;


                case 2:
                    System.out.println("\n========== CRIAR CONTA POUPANÇA ========="); 
                    System.out.print("Nome: ");
                    String nome_poupanca = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf_poupanca = sc.nextLine();

                    if (!Banco.validarCPF(cpf_poupanca)) {
                        System.out.println("CPF inválido. Tente novamente.");
                    } else {
                        boolean existe = false;
                        for (Conta c : contas) {
                            if (c.getCpf().equals(cpf_poupanca)) {
                                if (!c.nome.equalsIgnoreCase(nome_poupanca)) {
                                    System.out.println("CPF já está cadastrado com outro nome.");
                                    System.out.println("Nome esperado: " + c.nome);
                                    existe = true;
                                    break;
                                }
                                if (c instanceof ContaPoupanca) {
                                    System.out.println("Já existe uma conta POUPANÇA com este CPF.");
                                    System.out.println("========== Conta já existente =========");
                                    System.out.println("N° da conta: " + c.getNumero());
                                    System.out.println("Tipo de conta: Poupança");
                                    System.out.println("Nome: " + c.nome);
                                    System.out.println("CPF: " + c.getCpf());
                                    System.out.println("=======================================");
                                    existe = true;
                                    break;
                                }
                            }
                        }
                        if (!existe) {
                            contas.add(new ContaPoupanca(numeroSequencial, nome_poupanca, cpf_poupanca));
                            System.out.println("========== Conta criada com sucesso! =========");
                            System.out.println("N° da conta: " + numeroSequencial);
                            System.out.println("Tipo de conta: Poupança");
                            System.out.println("Nome: " + nome_poupanca);
                            System.out.println("CPF: " + cpf_poupanca);
                            System.out.println("==============================================");
                            numeroSequencial++;
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n========== DEPOSITAR DINHEIRO =========");   
                    System.out.print("Número da conta: ");
                    int n = sc.nextInt();
                    Conta c = buscarContaPorNumero(n);
                    if (c != null) {
                        System.out.print("Valor: ");
                        String entrada = sc.next().replace(",", ".");
                        double valor = Double.parseDouble(entrada);
                        c.depositar(valor);

                    }else {
                        System.out.println("==============================================");
                        System.out.println("Conta não encontrada. Tente novamente.");
                    }
                    break;
                case 4:
                    System.out.println("\n========== SACAR DINHEIRO ========="); 
                    System.out.print("Número da conta: ");
                    c = buscarContaPorNumero(sc.nextInt());
                    if (c != null) {
                        System.out.print("Valor: ");
                        String entrada = sc.next().replace(",", ".");
                        double valor = Double.parseDouble(entrada);
                        c.sacar(valor);
                    }else {
                        System.out.println("==============================================");
                        System.out.println("Conta não encontrada. Tente novamente.");
                    }
                    break;
                case 5:
                    System.out.println("\n========== APLICAR CORREÇÃO ========="); 
                    System.out.print("Taxa %: ");
                    String entrada = sc.next().replace(",", ".");
                    double t = Double.parseDouble(entrada);
                    System.out.println("Aplicando correção em contas poupança...");
                    for (Conta conta : contas) {
                        if (conta instanceof ContaPoupanca) {
                            System.out.println("Correção aplicada à conta: " + conta.getNumero());
                            ((ContaPoupanca) conta).aplicarCorrecao(t);
                        }
                    }

                    break;
                case 6:
                    System.out.println("\n========== CADASTRAR PIX ========="); 
                    System.out.print("CPF: ");
                    cpf = sc.next();
                    if (!Banco.validarCPF(cpf)) {
                        System.out.println("CPF inválido. Tente novamente.");
                    } else {
                        Conta contaPix = null;

                        // Procura especificamente uma conta corrente com esse CPF
                        for (Conta conta : contas) {
                            if (conta.getCpf().equals(cpf) && conta instanceof ContaCorrente) {
                                contaPix = conta;
                                break;
                            }
                        }

                        if (contaPix == null) {
                            System.out.println("Este CPF não pertence a uma conta corrente.\nApenas contas correntes podem usar PIX.");
                        } else {
                            ((ContaCorrente) contaPix).cadastrarPix(cpf);
                        }
                    }
                    System.out.println("=========================================");
                    break;


                case 7:
                    System.out.println("\n========== EFETUAR PIX =========");
                    System.out.print("Número da conta origem: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Entrada inválida!");
                        System.out.println( "Digite o número da conta como um número inteiro.");
                        sc.nextLine(); // limpa a entrada inválida
                        System.out.println("==============================================");
                        break;
                    }

                    int numContaOrigem = sc.nextInt();
                    sc.nextLine(); // consome o \n pendente

                    Conta origem = buscarContaPorNumero(numContaOrigem);

                    if (origem instanceof ContaCorrente) {
                        if (!cpfsPix.contains(origem.getCpf())) {
                            System.out.println("A conta de origem não está cadastrada no PIX.");
                            System.out.println("==============================================");
                            break;
                        }

                        System.out.print("CPF destino: ");
                        String destCpf = sc.next();

                        if (!cpfsPix.contains(destCpf)) {
                            System.out.println("O CPF de destino não está cadastrado no PIX.");
                            System.out.println("==============================================");
                            break;
                        }

                        System.out.print("Valor: ");
                        String entrada_pix = sc.next().replace(",", ".");
                        double valor = Double.parseDouble(entrada_pix);

                        ((ContaCorrente) origem).efetuarPix(destCpf, valor);
                    } else {
                        System.out.println("A conta de origem não é uma conta corrente ou não foi encontrada.");
                    }

                    System.out.println("==============================================");
                    break;



                case 8:
                    System.out.print("Número da conta: ");
                    int numeroConta = sc.nextInt();
                    c = buscarContaPorNumero(numeroConta);
                    if (c != null) {
                        c.getExtrato();
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
        sc.close();
    }
}
