package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;

public class PrepararPedidoUsecase {

    private final PedidoGateway pedidoGateway;


    public PrepararPedidoUsecase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido prepararPedido(Long id) {
        Pedido pedido = this.pedidoGateway.buscarPorId(id);
        pedido.setStatus(StatusPedido.EM_PREPARACAO);
        return pedidoGateway.adicionarPedido(pedido);
    }
}
