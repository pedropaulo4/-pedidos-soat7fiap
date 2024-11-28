package br.com.soat7.fiap.pedidos.domain;


import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Produto {

    private Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private String descricao;
    private String imagem;

    public Produto(Long id) {
        this.id = id;
    }
    public Produto(Long id, String nome, Categoria categoria, BigDecimal preco, String descricao, String imagem) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public Produto(ProdutoEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.categoria = entity.getCategoria();
        this.preco = entity.getPreco();
        this.descricao = entity.getDescricao();
        this.imagem = entity.getImagem();
    }
}
