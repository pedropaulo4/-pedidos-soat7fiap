package br.com.soat7.fiap.pedidos.domain;

import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void deveCriarProdutoComId() {
        Long id = 1L;
        Produto produto = new Produto(id);
        assertNotNull(produto);
        assertEquals(id, produto.getId());
        assertNull(produto.getNome()); //Verifica os outros atributos como nulos
        assertNull(produto.getCategoria());
        assertNull(produto.getPreco());
        assertNull(produto.getDescricao());
        assertNull(produto.getImagem());
    }

    @Test
    void deveCriarProdutoComTodosOsAtributos() {
        Long id = 1L;
        String nome = "Produto Teste";
        Categoria categoria = Categoria.BEBIDA;
        BigDecimal preco = BigDecimal.valueOf(100.00);
        String descricao = "Descrição do produto";
        String imagem = "url_da_imagem.jpg";

        Produto produto = new Produto(id, nome, categoria, preco, descricao, imagem);

        assertNotNull(produto);
        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(categoria, produto.getCategoria());
        assertEquals(preco, produto.getPreco());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(imagem, produto.getImagem());
    }

    @Test
    void deveCriarProdutoAPartirDeProdutoEntity() {
        //Arrange
        Long id = 1L;
        String nome = "Produto de Teste";
        Categoria categoria = Categoria.LANCHE;
        BigDecimal preco = BigDecimal.valueOf(25.50);
        String descricao = "Descrição do produto Entity";
        String imagem = "url_imagem_entity.jpg";

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(id);
        produtoEntity.setNome(nome);
        produtoEntity.setCategoria(categoria);
        produtoEntity.setPreco(preco);
        produtoEntity.setDescricao(descricao);
        produtoEntity.setImagem(imagem);

        //Act
        Produto produto = new Produto(produtoEntity);

        //Assert
        assertNotNull(produto);
        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(categoria, produto.getCategoria());
        assertEquals(preco, produto.getPreco());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(imagem, produto.getImagem());


    }

    @Test
    void deveRetornarNullQuandoCriarProdutoComProdutoEntityNulo(){
        Produto produto = new Produto();
        assertNull(produto.getId());
    }


}