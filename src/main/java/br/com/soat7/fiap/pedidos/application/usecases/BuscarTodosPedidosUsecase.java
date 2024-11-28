package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BuscarTodosPedidosUsecase {

    private final PedidoGateway pedidoGateway;


    public BuscarTodosPedidosUsecase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }


    public List<Pedido> buscarPedidos() {

        List<Pedido> pedidos = pedidoGateway.buscarPedidos();

        List<Pedido> pedidosFiltrados = pedidos.stream()
                .filter(pedido -> !Objects.equals(pedido.getStatus(), StatusPedido.FINALIZADO))
                .collect(Collectors.toList());

        pedidosFiltrados.sort(Comparator.comparingInt(pedido -> pedido.getStatus().getOrder()));
        return pedidosFiltrados;
    }


}
