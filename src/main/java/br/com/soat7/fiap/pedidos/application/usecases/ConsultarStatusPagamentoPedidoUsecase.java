package br.com.soat7.fiap.pedidos.application.usecases;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public class ConsultarStatusPagamentoPedidoUsecase {
    private final PedidoGateway pedidoGateway;

    public ConsultarStatusPagamentoPedidoUsecase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }


    public Pedido consultarStatusPagamentoPedido(Long id) throws Exception {
        Pedido pedidoSalvo = pedidoGateway.buscarPorId(id);
        RestClient restClient = RestClient.create();
        if(Objects.isNull(pedidoSalvo)) {
            throw new Exception("Pedido não encontrado na base de dados");
        }

        return pedidoSalvo;
    }
}
