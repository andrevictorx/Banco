public interface Pix {
    void cadastrarPix(String cpf);
    void efetuarPix(String destinoCpf, double valor);
    void receberPix(String origemCpf, double valor);
}
