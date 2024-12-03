package br.com.soat7.fiap.pedidos.application.gateways;

import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;

import java.util.List;

public interface ProdutoGateway {

    Produto adicionarProduto (Produto produto);

    Produto editarProduto (Long id, Produto produto);

    void excluir (Long id);

    List<Produto> consultarPorCategoria(Categoria categoria);
}
