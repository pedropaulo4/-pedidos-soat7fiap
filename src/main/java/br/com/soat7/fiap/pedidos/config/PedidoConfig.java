package br.com.soat7.fiap.pedidos.config;


import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.application.usecases.*;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.infrastructure.gateways.PedidoRepositoryGateway;
import br.com.soat7.fiap.pedidos.infrastructure.integration.client.PagamentoClient;
import br.com.soat7.fiap.pedidos.infrastructure.integration.service.PagamentoService;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.PedidoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class PedidoConfig {

    @Bean
    PagamentoService pagamentoService(PagamentoClient pagamentoClient) {
        return new PagamentoService(pagamentoClient);
    }
    @Bean
    AdicionarPedidoUsecase criarPedidoUseCase(PedidoGateway pedidoGateway, PagamentoService pagamentoService) {
        return new AdicionarPedidoUsecase(pedidoGateway, pagamentoService);
    }

    @Bean
    BuscarTodosPedidosUsecase buscarTodosPedidosUseCase(PedidoGateway pedidoGateway) {
        return new BuscarTodosPedidosUsecase(pedidoGateway);
    }

    @Bean
    FinalizarPedidoUsecase finalizarPedidoUseCase(PedidoGateway pedidoGateway) {
        return new FinalizarPedidoUsecase(pedidoGateway);
    }

    @Bean
    MarcarPedidoProntoUsecase marcarPedidoProntoUseCase(PedidoGateway pedidoGateway) {
        return new MarcarPedidoProntoUsecase(pedidoGateway);
    }

    @Bean
    PrepararPedidoUsecase prepararPedidoUseCase(PedidoGateway pedidoGateway) {
        return new PrepararPedidoUsecase(pedidoGateway);
    }

    @Bean
    ConsultarStatusPagamentoPedidoUsecase consultarStatusPagamentoPedidoUsecase(PedidoGateway pedidoGateway) {
        return new ConsultarStatusPagamentoPedidoUsecase(pedidoGateway);
    }

    @Bean
    PedidoGateway pedidoGateway(PedidoRepository pedidoRepository) {
        return new PedidoRepositoryGateway(pedidoRepository);
    }
}
