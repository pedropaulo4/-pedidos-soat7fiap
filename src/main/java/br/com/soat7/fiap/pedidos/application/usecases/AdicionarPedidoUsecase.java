package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import br.com.soat7.fiap.pedidos.infrastructure.integration.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

public class AdicionarPedidoUsecase {

    private final PedidoGateway pedidoGateway;

    private final PagamentoService pagamentoService;


    public AdicionarPedidoUsecase(PedidoGateway pedidoGateway, PagamentoService pagamentoService) {
        this.pedidoGateway = pedidoGateway;
        this.pagamentoService = pagamentoService;
    }

    public Pedido criarPedido(Pedido pedido) throws Exception {
        pedido.setDataCadastro(new Date());
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);

        pedido = pedidoGateway.adicionarPedido(pedido);

        pedido = pagamentoService.efetuarPagamento(pedido);

        if(StatusPagamento.REPROVADO.equals(pedido.getStatusPagamento())) {
            throw new Exception("Pedido cancelado porque o pagamento do pedido foi reprovado, tente novamente.");
        }

        return pedido;
    }
}
