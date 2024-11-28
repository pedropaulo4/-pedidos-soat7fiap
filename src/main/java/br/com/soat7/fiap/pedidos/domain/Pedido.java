package br.com.soat7.fiap.pedidos.domain;

import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
//import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    private Long id;
    private String cpf;
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataCadastro;
    private StatusPedido status;
    private List<Produto> produtoList;
    private String observacao;

    private StatusPagamento statusPagamento;

    public Pedido(Long id) {
        this.id = id;
    }

    public Pedido(Long id, String cpf, Date dataCadastro, StatusPedido status, List<ProdutoEntity> produtoList, String observacao, StatusPagamento statusPagamento) {
        this.id = id;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
        this.status = status;
        this.produtoList = produtoList.stream().map(Produto::new).toList();
        this.observacao = observacao;
        this.statusPagamento = statusPagamento;
    }
}
