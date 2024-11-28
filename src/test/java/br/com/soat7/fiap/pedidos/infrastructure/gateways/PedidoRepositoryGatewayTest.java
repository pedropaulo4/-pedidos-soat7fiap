package br.com.soat7.fiap.pedidos.infrastructure.gateways;

import br.com.soat7.fiap.pedidos.application.gateways.PedidoGateway;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.infrastructure.gateways.PedidoRepositoryGateway;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoRepositoryGatewayTest {

    @Mock
    private PedidoRepository repository;

    @InjectMocks
    private PedidoRepositoryGateway gateway;

    @Test
    void adicionarPedido_deveRetornarPedidoSalvo() {
        Pedido pedido = new Pedido();
        pedido.setId(1L); // Setando um ID para facilitar a verificação
        when(repository.save(pedido)).thenReturn(pedido);

        Pedido pedidoSalvo = gateway.adicionarPedido(pedido);

        assertNotNull(pedidoSalvo);
        assertEquals(1L, pedidoSalvo.getId());
        verify(repository).save(pedido);
    }

    @Test
    void buscarPedidos_deveRetornarListaDePedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido());
        when(repository.findAll()).thenReturn(pedidos);

        List<Pedido> pedidosObtidos = gateway.buscarPedidos();

        assertNotNull(pedidosObtidos);
        assertEquals(1, pedidosObtidos.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPedidos_deveRetornarListaVaziaQuandoNaoHouverPedidos() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Pedido> pedidosObtidos = gateway.buscarPedidos();

        assertNotNull(pedidosObtidos);
        assertTrue(pedidosObtidos.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarPedidoQuandoExiste() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        when(repository.findById(id)).thenReturn(pedido);

        Pedido pedidoObtido = gateway.buscarPorId(id);

        assertNotNull(pedidoObtido);
        assertEquals(id, pedidoObtido.getId());
        verify(repository).findById(id);
    }

    @Test
    void buscarPorId_deveRetornarNullQuandoPedidoNaoExiste() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(null);

        Pedido pedidoObtido = gateway.buscarPorId(id);

        assertNull(pedidoObtido);
        verify(repository).findById(id);
    }

    @Test
    void adicionarPedido_deveLancarExceptionQuandoRepositorioFalhar(){
        Pedido pedido = new Pedido();
        doThrow(new RuntimeException("Erro no repositório")).when(repository).save(pedido);
        assertThrows(RuntimeException.class, ()-> gateway.adicionarPedido(pedido));
    }
}