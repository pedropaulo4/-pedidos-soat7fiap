package br.com.soat7.fiap.pedidos.infrastructure.service;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.infrastructure.integration.client.PagamentoClient;
import br.com.soat7.fiap.pedidos.infrastructure.integration.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoClient pagamentoClient;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1L);
    }

    @Test
    void efetuarPagamento_deveRetornarPedidoComStatusPagamentoAprovado() {
        Pedido pedidoAprovado = new Pedido();
        pedidoAprovado.setId(1L);
        pedidoAprovado.setStatusPagamento(StatusPagamento.APROVADO);
        ResponseEntity<Pedido> response = ResponseEntity.ok(pedidoAprovado);
        when(pagamentoClient.efetuarPagamento(pedido)).thenReturn(response);

        Pedido resultado = pagamentoService.efetuarPagamento(pedido);

        assertNotNull(resultado);
        assertEquals(StatusPagamento.APROVADO, resultado.getStatusPagamento());
    }


    @Test
    void efetuarPagamento_deveRetornarPedidoComStatusPagamentoReprovado() {
        Pedido pedidoReprovado = new Pedido();
        pedidoReprovado.setId(1L);
        pedidoReprovado.setStatusPagamento(StatusPagamento.REPROVADO);
        ResponseEntity<Pedido> response = ResponseEntity.ok(pedidoReprovado);
        when(pagamentoClient.efetuarPagamento(pedido)).thenReturn(response);

        Pedido resultado = pagamentoService.efetuarPagamento(pedido);

        assertNotNull(resultado);
        assertEquals(StatusPagamento.REPROVADO, resultado.getStatusPagamento());
    }

    @Test
    void efetuarPagamento_deveLancarException_quandoPagamentoClientRetornarNull(){
        when(pagamentoClient.efetuarPagamento(pedido)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> pagamentoService.efetuarPagamento(pedido));
    }

    @Test
    void efetuarPagamento_deveRetornarPedidoComStatusPagamentoAguardando() {
      Pedido pedidoAguardando = new Pedido();
      pedidoAguardando.setId(1L);
      pedidoAguardando.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);
      ResponseEntity<Pedido> response = ResponseEntity.ok(pedidoAguardando);
      when(pagamentoClient.efetuarPagamento(pedido)).thenReturn(response);

      Pedido resultado = pagamentoService.efetuarPagamento(pedido);

      assertNotNull(resultado);
      assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, resultado.getStatusPagamento());
    }

}