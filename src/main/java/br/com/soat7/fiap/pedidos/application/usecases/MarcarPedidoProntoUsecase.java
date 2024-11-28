package br.com.soat7.fiap.pedidos.application.usecases;

import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;

public class MarcarPedidoProntoUsecase {

    private final PedidoGateway pedidoGateway;


    public MarcarPedidoProntoUsecase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }


    public Pedido marcarComoPronto(Long id) {
        Pedido pedido = this.pedidoGateway.buscarPorId(id);
        pedido.setStatus(StatusPedido.PRONTO);
        return pedidoGateway.adicionarPedido(pedido);
    }
}
