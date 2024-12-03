package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;

public class ExcluirProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public ExcluirProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public void excluir(Long id) {
        produtoGateway.excluir(id);
    }
}
