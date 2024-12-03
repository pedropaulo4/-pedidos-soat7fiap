package br.com.soat7.fiap.pedidos.infrastructure.persistence.entity;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Getter
@Setter
@NoArgsConstructor
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String cpf;
    private Date dataCadastro;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @ManyToMany()
    @JoinTable(
            name = "PedidoProduto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<ProdutoEntity> produtoList;
    private String observacao;

    public PedidoEntity(Pedido pedido) {
        this.id = pedido.getId();
        this.cpf = pedido.getCpf();
        this.dataCadastro = pedido.getDataCadastro();
        this.status = pedido.getStatus();
        this.produtoList = pedido.getProdutoList().stream().map(ProdutoEntity::new).toList();
        this.observacao = pedido.getObservacao();
        this.statusPagamento = pedido.getStatusPagamento();
    }

    public Pedido toPedido () {
        return new Pedido(this.id, this.cpf, this.dataCadastro, this.status, this.produtoList, this.observacao, this.statusPagamento );
    }
}
