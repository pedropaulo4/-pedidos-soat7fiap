package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;
import br.com.soat7.fiap.pedidos.domain.Produto;

public class EditarProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public EditarProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto editarProduto(Long id, Produto produto) {
        return produtoGateway.editarProduto(id,produto);
    }
}
