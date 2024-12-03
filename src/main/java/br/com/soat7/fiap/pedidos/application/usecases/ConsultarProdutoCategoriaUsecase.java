package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;

import java.util.List;

public class ConsultarProdutoCategoriaUsecase {

    private final ProdutoGateway produtoGateway;

    public ConsultarProdutoCategoriaUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public List<Produto> consultarPorCategoria(Categoria categoria) {
        return produtoGateway.consultarPorCategoria(categoria);
    }
}
