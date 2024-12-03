package br.com.soat7.fiap.pedidos.infrastructure.persistence;

import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;


@Component
public class ProdutoRepository  {

    private final IProdutoRepository repository;

    public ProdutoRepository(IProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto save(Produto produto) {

        ProdutoEntity produtoEntity = new ProdutoEntity(produto);

        try {
            ProdutoEntity produtoSalvo = this.repository.save(produtoEntity);
            produto.setId(produtoSalvo.getId());
            return produto;
        } catch (Exception e) {
            String message = "Erro ao salvar os valores informados. Verifique as valores enviados.";
            if (e.getMessage().contains("Unique index or primary key violation")){
                message = "Nome do produto já existente.";
            }
            throw new DataIntegrityViolationException(message);
        }
    }

    public void delete(Long id) {
        this.buscarPeloId(id);
        repository.deleteById(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto produtoSalvo = buscarPeloId(id);
        ProdutoEntity produtoEntity = new ProdutoEntity(produtoSalvo);
        produtoEntity.atualizar(produto);
        return this.repository.save(produtoEntity).toProduto();
    }

    public Produto buscarPeloId(Long id) {
        return repository.findById(id)
                .map(ProdutoEntity::toProduto)
                .orElseThrow(() -> new NoSuchElementException(String.format("O Produto [%s] não foi encontrado.", id)));
    }

    public List<Produto> consultarPorCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria).stream()
                .map(ProdutoEntity::toProduto)
                .toList();
    }

}
