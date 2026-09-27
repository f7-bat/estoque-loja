# Sistema de Estoque de Produtos

Sistema desenvolvido em **Java** para gerenciamento de produtos em estoque, utilizando conceitos de **Programação Orientada a Objetos (POO)**.

O projeto foi desenvolvido como exercício de implementação envolvendo classes abstratas, herança, interfaces, polimorfismo, composição e tratamento de exceções.

## Objetivo

Criar um sistema capaz de:

- Cadastrar produtos comuns e perecíveis;
- Controlar a quantidade disponível em estoque;
- Realizar vendas;
- Impedir vendas acima da quantidade disponível;
- Validar preços e quantidades;
- Calcular o valor total do estoque;
- Aplicar regras diferentes de cálculo para produtos perecíveis;
- Demonstrar os principais conceitos de POO em Java.

## Tecnologias utilizadas

- **Java**
- Programação Orientada a Objetos
- `ArrayList`
- Tratamento de exceções

## Estrutura do projeto

```text
src/
├── EstoqueException.java
├── QuantidadeInvalidaException.java
├── ProdutoIndisponivelException.java
├── Vendavel.java
├── Product.java
├── ProdutoComum.java
├── ProdutoPerecivel.java
├── Estoque.java
└── EstoqueApp.java
```

## Conceitos de POO utilizados

### Classe abstrata

A classe `Product` é abstrata e representa a estrutura comum dos produtos.

Ela possui:

- `nome`
- `preco`
- `quantidade`

Além disso, define o método abstrato:

```java
public abstract double calcularValorTotal();
```

Cada tipo de produto implementa esse método de acordo com sua própria regra.

### Herança

As classes:

```text
ProdutoComum
ProdutoPerecivel
```

herdam de:

```text
Product
```

Exemplo:

```java
public class ProdutoComum extends Product
```

e:

```java
public class ProdutoPerecivel extends Product
```

### Polimorfismo dinâmico

O método `calcularValorTotal()` é sobrescrito pelas subclasses.

Para produtos comuns:

```java
return getPreco() * getQuantidade();
```

Para produtos perecíveis, existe uma regra adicional: produtos com até 3 dias para vencer recebem automaticamente 20% de desconto no cálculo do valor total.

O `Estoque` trabalha apenas com objetos do tipo `Product`:

```java
for (Product produto : produtos) {
    total += produto.calcularValorTotal();
}
```

Mesmo assim, o Java executa automaticamente a implementação correta de cada produto.

### Interface

A interface `Vendavel` define o comportamento de venda:

```java
public interface Vendavel {

    void vender(int quantidadeDesejada)
            throws ProdutoIndisponivelException;
}
```

A classe `Product` implementa essa interface.

### Sobrecarga de métodos

A classe `Product` possui duas versões de `aplicarDesconto()`:

```java
public void aplicarDesconto(double percentual)
```

e:

```java
public void aplicarDesconto(
        double percentual,
        double descontoMaximo
)
```

As duas possuem o mesmo nome, mas parâmetros diferentes, caracterizando **sobrecarga de métodos**.

### Composição

A classe `Estoque` possui uma lista de produtos:

```java
private List<Product> produtos = new ArrayList<>();
```

Dessa forma, o estoque é responsável por armazenar e gerenciar os produtos.

## Hierarquia de exceções

O projeto possui uma exceção base:

```text
EstoqueException
├── QuantidadeInvalidaException
└── ProdutoIndisponivelException
```

### EstoqueException

É a exceção base do sistema.

### QuantidadeInvalidaException

É lançada quando um produto é cadastrado com:

- preço negativo;
- quantidade negativa.

Exemplo:

```java
new ProdutoComum(
    "Produto Inválido",
    10.00,
    -5
);
```

### ProdutoIndisponivelException

É lançada quando uma venda tenta retirar uma quantidade maior que a disponível no estoque.

Exemplo:

```java
estoque.venderProduto(0, 100000);
```

## Produtos cadastrados no exemplo

O programa principal cadastra:

| Produto         | Tipo      |    Preço | Quantidade | Validade |
| --------------- | --------- | -------: | ---------: | -------: |
| Parafuso        | Comum     |  R$ 0,50 |        500 |        — |
| Fita Isolante   | Comum     |  R$ 8,90 |        100 |        — |
| Cola de Contato | Perecível | R$ 15,00 |         30 |  10 dias |
| Tinta Spray     | Perecível | R$ 22,00 |         20 |   2 dias |

A `Tinta Spray` possui 2 dias para vencer, portanto recebe a regra de **20% de desconto** no cálculo do valor total.

## Testes realizados

O programa demonstra os seguintes casos:

### Cadastro válido

São cadastrados:

- 2 produtos comuns;
- 2 produtos perecíveis.

### Cadastro inválido

É realizada uma tentativa de cadastrar um produto com quantidade negativa.

A exceção `QuantidadeInvalidaException` é capturada.

### Venda válida

É realizada uma venda de 50 unidades de `Parafuso`.

A quantidade é reduzida de:

```text
500
```

para:

```text
450
```

### Venda inválida

Em seguida, o programa tenta vender 100000 unidades de `Parafuso`.

Como não existe essa quantidade disponível, `ProdutoIndisponivelException` é lançada e capturada.

## Como executar

### 1. Verifique se o Java está instalado

No terminal:

```bash
java -version
```

e:

```bash
javac -version
```

### 2. Compile os arquivos

Na pasta onde estão os arquivos `.java`:

```bash
javac *.java
```

### 3. Execute o programa

```bash
java EstoqueApp
```

## Exemplo de saída

```text
Falha esperada ao cadastrar produto: Quantidade inválida para o produto 'Produto Inválido': -5 (não pode ser negativa).

=== PRODUTOS CADASTRADOS ===
Parafuso | Preço: R$ 0.50 | Quantidade: 500
Fita Isolante | Preço: R$ 8.90 | Quantidade: 100
Cola de Contato | Preço: R$ 15.00 | Quantidade: 30 | Dias para vencer: 10
Tinta Spray | Preço: R$ 22.00 | Quantidade: 20 | Dias para vencer: 2

=== TESTANDO VENDAS ===
Venda de 50 unidades de Parafuso realizada com sucesso.
Falha esperada na venda: Estoque insuficiente para 'Parafuso'. Disponível: 450, solicitado: 100000.

=== VALOR TOTAL DO ESTOQUE: R$ 1731.00 ===
```

## Funcionamento do cálculo

O valor dos produtos comuns é calculado normalmente:

```text
preço × quantidade
```

Para produtos perecíveis com até 3 dias para vencer:

```text
(preço × quantidade) × 0,80
```

No exemplo:

```text
Tinta Spray
R$ 22,00 × 20 = R$ 440,00

20% de desconto:

R$ 440,00 × 0,80 = R$ 352,00
```

Após a venda de 50 parafusos:

```text
Parafuso:
R$ 0,50 × 450 = R$ 225,00

Fita Isolante:
R$ 8,90 × 100 = R$ 890,00

Cola de Contato:
R$ 15,00 × 30 = R$ 450,00

Tinta Spray:
R$ 22,00 × 20 × 0,80 = R$ 352,00
```

Total:

```text
R$ 225,00
+ R$ 890,00
+ R$ 450,00
+ R$ 352,00
----------------
R$ 1.917,00
```
