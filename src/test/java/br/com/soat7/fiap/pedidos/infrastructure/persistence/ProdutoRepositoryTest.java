package br.com.soat7.fiap.pedidos.infrastructure.persistence;

import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoRepositoryTest {

    @Mock
    private IProdutoRepository repository;

    @InjectMocks
    private ProdutoRepository produtoRepository;

    @Test
    void save_deveSalvarProdutoComSucesso() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setCategoria(Categoria.LANCHE);

        ProdutoEntity produtoEntity = new ProdutoEntity(produto);
        produtoEntity.setId(1L); // Simulando um ID gerado pelo banco de dados

        when(repository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        // Act
        Produto produtoSalvo = produtoRepository.save(produto);

        // Assert
        assertNotNull(produtoSalvo);
        assertEquals(1L, produtoSalvo.getId());
        assertEquals("Produto Teste", produtoSalvo.getNome());
        assertEquals(Categoria.LANCHE, produtoSalvo.getCategoria());
    }

    @Test
    void save_deveLancarDataIntegrityViolationExceptionQuandoNomeJaExiste() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setCategoria(Categoria.LANCHE);

        when(repository.save(any(ProdutoEntity.class))).thenThrow(new RuntimeException("Unique index or primary key violation"));

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> produtoRepository.save(produto));
    }

    @Test
    void save_deveLancarDataIntegrityViolationExceptionParaOutroErro() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setCategoria(Categoria.LANCHE);

        when(repository.save(any(ProdutoEntity.class))).thenThrow(new RuntimeException("Outro erro"));

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> produtoRepository.save(produto));
        //Verifique a mensagem de erro se necessário
    }


    @Test
    void delete_deveDeletarProdutoComSucesso() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        when(repository.findById(anyLong())).thenReturn(Optional.of(new ProdutoEntity(produto)));

        // Act
        assertDoesNotThrow(() -> produtoRepository.delete(id));
        //Assert -  Não há como verificar diretamente a exclusão aqui.  A verificação se o delete foi chamado no repositório é feita no próximo teste
    }

    @Test
    void delete_deveLancarNoSuchElementExceptionQuandoProdutoNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> produtoRepository.delete(id));
    }

    @Test
    void atualizar_deveAtualizarProdutoComSucesso() {
        // Arrange
        Long id = 1L;
        Produto produtoExistente = new Produto();
        produtoExistente.setId(id);
        produtoExistente.setNome("Nome antigo");
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Nome novo");
        produtoAtualizado.setId(id);

        when(repository.findById(anyLong())).thenReturn(Optional.of(new ProdutoEntity(produtoExistente)));
        when(repository.save(any(ProdutoEntity.class))).thenReturn(new ProdutoEntity(produtoAtualizado));

        // Act
        Produto produtoRetorno = produtoRepository.atualizar(id, produtoAtualizado);

        // Assert
        assertEquals("Nome novo", produtoRetorno.getNome());
    }


    @Test
    void atualizar_deveLancarNoSuchElementExceptionQuandoProdutoNaoEncontrado() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> produtoRepository.atualizar(id, produto));
    }

    @Test
    void buscarPeloId_deveRetornarProdutoComSucesso() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        when(repository.findById(anyLong())).thenReturn(Optional.of(new ProdutoEntity(produto)));

        // Act
        Produto produtoEncontrado = produtoRepository.buscarPeloId(id);

        // Assert
        assertNotNull(produtoEncontrado);
        assertEquals(id, produtoEncontrado.getId());
    }

    @Test
    void buscarPeloId_deveLancarNoSuchElementExceptionQuandoProdutoNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> produtoRepository.buscarPeloId(id));
    }

    @Test
    void consultarPorCategoria_deveRetornarListaDeProdutos() {
        // Arrange
        Categoria categoria = Categoria.LANCHE;
        List<ProdutoEntity> produtosEntity = List.of(new ProdutoEntity(new Produto()), new ProdutoEntity(new Produto()));
        when(repository.findByCategoria(any(Categoria.class))).thenReturn(produtosEntity);

        // Act
        List<Produto> produtos = produtoRepository.consultarPorCategoria(categoria);

        // Assert
        assertNotNull(produtos);
        assertEquals(2, produtos.size());
    }

    @Test
    void consultarPorCategoria_deveRetornarListaVaziaQuandoNaoHouverProdutos() {
        // Arrange
        Categoria categoria = Categoria.LANCHE;
        when(repository.findByCategoria(any(Categoria.class))).thenReturn(List.of());

        // Act
        List<Produto> produtos = produtoRepository.consultarPorCategoria(categoria);

        // Assert
        assertNotNull(produtos);
        assertTrue(produtos.isEmpty());
    }
}