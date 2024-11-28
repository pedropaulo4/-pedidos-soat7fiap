package br.com.soat7.fiap.pedidos.infrastructure.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PedidoResponse {

    private Long id;
    private String cpf;
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataCadastro;
    private StatusPedido status;
    private StatusPagamento statusPagamento;
    private List<Produto> produtoList;
    private String observacao;
}
