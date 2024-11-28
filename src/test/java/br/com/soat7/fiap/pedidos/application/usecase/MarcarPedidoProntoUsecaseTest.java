package br.com.soat7.fiap.pedidos.application.usecase;

import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.application.usecases.MarcarPedidoProntoUsecase;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarcarPedidoProntoUsecaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private MarcarPedidoProntoUsecase marcarPedidoProntoUsecase;

    @Test
    void marcarComoPronto_deveAtualizarStatusParaPronto_quandoPedidoExiste() {
        // Arrange
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(StatusPedido.RECEBIDO); // ou qualquer outro status inicial

        when(pedidoGateway.buscarPorId(id)).thenReturn(pedido);
        when(pedidoGateway.adicionarPedido(any(Pedido.class))).thenReturn(pedido); // Simula a atualização

        // Act
        Pedido pedidoPronto = marcarPedidoProntoUsecase.marcarComoPronto(id);

        // Assert
        assertNotNull(pedidoPronto);
        assertEquals(id, pedidoPronto.getId());
        assertEquals(StatusPedido.PRONTO, pedidoPronto.getStatus());
    }


    @Test
    void marcarComoPronto_deveLancarException_quandoGatewayFalhar() {
        // Arrange
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(StatusPedido.RECEBIDO); // ou qualquer outro status inicial
        when(pedidoGateway.buscarPorId(id)).thenReturn(pedido);
        // Simula uma exceção no gateway
        doThrow(new RuntimeException("Erro no Gateway")).when(pedidoGateway).adicionarPedido(any(Pedido.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> marcarPedidoProntoUsecase.marcarComoPronto(id));
    }
}