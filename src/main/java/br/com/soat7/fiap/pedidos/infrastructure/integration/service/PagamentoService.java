package br.com.soat7.fiap.pedidos.infrastructure.integration.service;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.infrastructure.integration.client.PagamentoClient;

public class PagamentoService {

    private PagamentoClient pagamentoClient;

    public PagamentoService(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }

    public Pedido efetuarPagamento(final Pedido pedido) {
        return pagamentoClient.efetuarPagamento(pedido).getBody();
    }
}
