public class EstoqueApp {

    public static void main(String[] args) {

        Estoque estoque = new Estoque();

        // ==========================================
        // CADASTRO DOS PRODUTOS
        // ==========================================

        try {

            // 2 produtos comuns
            estoque.adicionarProduto(
                    new ProdutoComum(
                            "Parafuso",
                            0.50,
                            500
                    )
            );

            estoque.adicionarProduto(
                    new ProdutoComum(
                            "Fita Isolante",
                            8.90,
                            100
                    )
            );

            // 2 produtos perecíveis
            estoque.adicionarProduto(
                    new ProdutoPerecivel(
                            "Cola de Contato",
                            15.00,
                            30,
                            10
                    )
            );

            // Produto com <= 3 dias para vencer
            estoque.adicionarProduto(
                    new ProdutoPerecivel(
                            "Tinta Spray",
                            22.00,
                            20,
                            2
                    )
            );

        } catch (QuantidadeInvalidaException e) {

            System.out.println(
                    "Erro inesperado ao cadastrar produto: "
                    + e.getMessage()
            );
        }

        // ==========================================
        // TENTATIVA DE CADASTRO INVÁLIDO
        // ==========================================

        try {

            estoque.adicionarProduto(
                    new ProdutoComum(
                            "Produto Inválido",
                            10.00,
                            -5
                    )
            );

        } catch (QuantidadeInvalidaException e) {

            System.out.println(
                    "Falha esperada ao cadastrar produto: "
                    + e.getMessage()
            );
        }

        // ==========================================
        // PRODUTOS CADASTRADOS
        // ==========================================

        System.out.println();
        System.out.println("=== PRODUTOS CADASTRADOS ===");

        for (Product produto : estoque.getProdutos()) {
            System.out.println(produto.getDescricao());
        }

        // ==========================================
        // TESTANDO VENDAS
        // ==========================================

        System.out.println();
        System.out.println("=== TESTANDO VENDAS ===");

        // Venda válida
        try {

            estoque.venderProduto(0, 50);

            System.out.println(
                    "Venda de 50 unidades de Parafuso realizada com sucesso."
            );

        } catch (ProdutoIndisponivelException e) {

            System.out.println(
                    "Erro inesperado na venda válida: "
                    + e.getMessage()
            );
        }

        // Venda inválida
        try {

            estoque.venderProduto(0, 100000);

            System.out.println(
                    "Venda realizada com sucesso."
            );

        } catch (ProdutoIndisponivelException e) {

            System.out.println(
                    "Falha esperada na venda: "
                    + e.getMessage()
            );
        }

        // ==========================================
        // VALOR TOTAL DO ESTOQUE
        // ==========================================

        System.out.println();

        System.out.printf(
                "=== VALOR TOTAL DO ESTOQUE: R$ %.2f ===%n",
                estoque.calcularValorTotalEstoque()
        );
    }
}