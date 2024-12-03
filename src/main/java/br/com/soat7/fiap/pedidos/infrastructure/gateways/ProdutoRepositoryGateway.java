package br.com.soat7.fiap.pedidos.infrastructure.gateways;


import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.ProdutoRepository;

import java.util.List;

public class ProdutoRepositoryGateway implements ProdutoGateway {

    private final ProdutoRepository repository;

    public ProdutoRepositoryGateway(ProdutoRepository repository) {
        this.repository = repository;
    }


    @Override
    public Produto adicionarProduto(Produto produto) {
        return this.repository.save(produto);
    }

    @Override
    public Produto editarProduto(Long id, Produto produto) {
        return this.repository.atualizar(id, produto);
    }

    @Override
    public void excluir(Long id) {
        repository.delete(id);
    }

    @Override
    public List<Produto> consultarPorCategoria(Categoria categoria) {
        return repository.consultarPorCategoria(categoria);
    }
}
