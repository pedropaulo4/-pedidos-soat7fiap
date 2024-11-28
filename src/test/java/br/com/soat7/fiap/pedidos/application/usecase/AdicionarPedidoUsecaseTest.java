package br.com.soat7.fiap.pedidos.application.usecase;

import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.application.usecases.AdicionarPedidoUsecase;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import br.com.soat7.fiap.pedidos.infrastructure.integration.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdicionarPedidoUsecaseTest {

    @Mock
    private PedidoGateway pedidoGateway;
    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private AdicionarPedidoUsecase adicionarPedidoUsecase;

    @Test
    void criarPedido_deveCriarUmNovoPedidoComStatusCorreto_quandoPagamentoAprovado() throws Exception {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L); // Adicione um ID para facilitar a verificação
        // ... outros dados do pedido ...

        Pedido pedidoAprovado = new Pedido();
        pedidoAprovado.setId(1L);
        pedidoAprovado.setStatus(StatusPedido.RECEBIDO);
        pedidoAprovado.setStatusPagamento(StatusPagamento.APROVADO); // Simulando aprovação
        // ... outros dados ...

        when(pagamentoService.efetuarPagamento(any(Pedido.class))).thenReturn(pedidoAprovado);
        when(pedidoGateway.adicionarPedido(any(Pedido.class))).thenReturn(pedidoAprovado);

        // Act
        Pedido resultado = adicionarPedidoUsecase.criarPedido(pedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusPedido.RECEBIDO, resultado.getStatus());
        assertEquals(StatusPagamento.APROVADO, resultado.getStatusPagamento()); // Verifica se o status está correto
    }

    @Test
    void criarPedido_deveLancarException_quandoPagamentoReprovado() throws Exception {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        // ... outros dados ...

        Pedido pedidoReprovado = new Pedido();
        pedidoReprovado.setId(1L);
        pedidoReprovado.setStatusPagamento(StatusPagamento.REPROVADO);

        when(pagamentoService.efetuarPagamento(any(Pedido.class))).thenReturn(pedidoReprovado);

        // Act & Assert
        assertThrows(Exception.class, () -> adicionarPedidoUsecase.criarPedido(pedido));
    }


    @Test
    void criarPedido_deveLancarException_quandoGatewayFalhar() throws Exception {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        // ... outros dados ...

        Pedido pedidoAprovado = new Pedido();
        pedidoAprovado.setId(1L);
        pedidoAprovado.setStatusPagamento(StatusPagamento.APROVADO);
        when(pagamentoService.efetuarPagamento(any(Pedido.class))).thenReturn(pedidoAprovado);

        // Simulando erro no Gateway
        doThrow(new RuntimeException("Erro no gateway")).when(pedidoGateway).adicionarPedido(any(Pedido.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> adicionarPedidoUsecase.criarPedido(pedido));

    }
}