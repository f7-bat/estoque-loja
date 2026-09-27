public class ProdutoPerecivel extends Product {

    private int diasParaVencer;

    public ProdutoPerecivel(
            String nome,
            double preco,
            int quantidade,
            int diasParaVencer
    ) throws QuantidadeInvalidaException {

        super(nome, preco, quantidade);

        this.diasParaVencer = diasParaVencer;
    }

    @Override
    public double calcularValorTotal() {

        double valorTotal = getPreco() * getQuantidade();

        // Desconto automático de 20%
        // quando faltam 3 dias ou menos para vencer
        if (diasParaVencer <= 3) {
            valorTotal *= 0.80;
        }

        return valorTotal;
    }

    @Override
    public String getDescricao() {

        return String.format(
                "%s | Preço: R$ %.2f | Quantidade: %d | Dias para vencer: %d",
                getNome(),
                getPreco(),
                getQuantidade(),
                diasParaVencer
        );
    }
}