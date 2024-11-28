package br.com.soat7.fiap.pedidos.infrastructure.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;

import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;

import java.util.Date;

public class PedidoStatusPagamentoResponse {

    private Long id;
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataCadastro;
    private StatusPedido status;
    private StatusPagamento statusPagamento;
}
