package br.com.soat7.fiap.pedidos.infrastructure.persistence.entity;


import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produto", uniqueConstraints = { @UniqueConstraint(columnNames = {"nome", "categoria"})})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceProdutoId")
    private Long id;
    @Column(unique = true)
    private String nome;
    private BigDecimal preco;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String descricao;
    private String imagem;
    @ManyToMany(mappedBy = "produtoList")
    private List<PedidoEntity> pedidos;

    public void atualizar(Produto produto) {
        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
        this.descricao = produto.getDescricao();
        this.imagem = produto.getImagem();
    }

    public ProdutoEntity(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.descricao = produto.getDescricao();
        this.imagem = produto.getImagem();
    }

    public Produto toProduto () {
        return new Produto(this.id, this.nome, this.categoria, this.preco, this.descricao, this.imagem);
    }
}
