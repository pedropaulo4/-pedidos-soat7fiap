package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;

public class FinalizarPedidoUsecase {

    private final PedidoGateway pedidoGateway;


    public FinalizarPedidoUsecase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }


    public Pedido finalizarPedido(Long id) {
        Pedido pedido = this.pedidoGateway.buscarPorId(id);
        pedido.setStatus(StatusPedido.FINALIZADO);
        return pedidoGateway.adicionarPedido(pedido);
    }
}
