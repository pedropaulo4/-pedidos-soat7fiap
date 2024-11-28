package br.com.soat7.fiap.pedidos.application.usecase;

import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.application.usecases.ConsultarStatusPagamentoPedidoUsecase;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarStatusPagamentoPedidoUsecaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private ConsultarStatusPagamentoPedidoUsecase consultarStatusPagamentoPedidoUsecase;

    @Test
    void consultarStatusPagamentoPedido_deveRetornarPedido_quandoPedidoExiste() {

        Long id = 1L;
        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(id);

        when(pedidoGateway.buscarPorId(id)).thenReturn(pedidoSalvo);
        Pedido resultado = null;
        try {
            resultado = consultarStatusPagamentoPedidoUsecase.consultarStatusPagamentoPedido(id);
        } catch (Exception e) {
            fail("Deveria retornar o pedido, mas lançou exceção: " + e.getMessage());
        }

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    void consultarStatusPagamentoPedido_deveLancarException_quandoPedidoNaoExiste() {
        Long id = 1L;
        when(pedidoGateway.buscarPorId(id)).thenReturn(null);

        assertThrows(Exception.class, () -> consultarStatusPagamentoPedidoUsecase.consultarStatusPagamentoPedido(id));
    }
}