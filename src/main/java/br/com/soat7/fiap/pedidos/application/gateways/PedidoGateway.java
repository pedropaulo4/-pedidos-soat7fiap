package br.com.soat7.fiap.pedidos.application.gateways;




import br.com.soat7.fiap.pedidos.domain.Pedido;

import java.util.List;

public interface PedidoGateway {

    Pedido adicionarPedido(Pedido pedido);

    List<Pedido> buscarPedidos();

    Pedido buscarPorId(Long id);
}
