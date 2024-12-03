package br.com.soat7.fiap.pedidos.infrastructure.integration;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.infrastructure.integration.client.PagamentoClient;
import br.com.soat7.fiap.pedidos.infrastructure.integration.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoClient pagamentoClient;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void efetuarPagamento_deveRetornarPedidoComPagamentoEfetuado() {
        // Arrange
        Pedido pedido = new Pedido(); // Crie um objeto Pedido com dados de teste
        pedido.setId(1L); // Adicione um ID ou qualquer outro atributo necessário para seu teste
        Pedido pedidoPago = new Pedido(); //Pedido com status de pago
        pedidoPago.setId(1L);


        ResponseEntity<Pedido> response = ResponseEntity.ok(pedidoPago);
        when(pagamentoClient.efetuarPagamento(pedido)).thenReturn(response);

        // Act
        Pedido resultado = pagamentoService.efetuarPagamento(pedido);

        // Assert
        assertEquals(pedidoPago, resultado);

    }

}