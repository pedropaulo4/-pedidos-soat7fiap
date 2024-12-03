package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;
import br.com.soat7.fiap.pedidos.domain.Produto;

public class AdicionarProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public AdicionarProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto adicionarProduto(Produto produto) {
        return produtoGateway.adicionarProduto(produto);
    }
}
