package br.com.soat7.fiap.pedidos.infrastructure.persistence;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.PedidoEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoRepositoryTest {

    @Mock
    private IPedidoRepository repository;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PedidoRepository pedidoRepository;

    public Pedido populaValores() {
        Pedido pedido = new Pedido();
        Produto p1 = new Produto(1L,"teste", Categoria.BEBIDA, BigDecimal.TEN, "teste", "teste imagem");
        List<Produto> produtos = new ArrayList<>();
        produtos.add(p1);

        pedido.setId(1L);
        pedido.setCpf("70156056110");
        pedido.setDataCadastro(Date.valueOf(LocalDate.now()));
        pedido.setStatusPagamento(StatusPagamento.APROVADO);
        pedido.setProdutoList(produtos);
        pedido.setObservacao("Teste");
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setId(1L);

        return pedido;
    }


    @Test
    void save_deveSalvarPedidoComSucesso() {

        Pedido pedido = populaValores();

        PedidoEntity pedidoEntity = new PedidoEntity(pedido);
        when(repository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);
        when(repository.findById(pedidoEntity.getId())).thenReturn(Optional.of(pedidoEntity));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        assertNotNull(pedidoSalvo);
        assertEquals(1L, pedidoSalvo.getId());

    }


    @Test
    void save_deveLancarDataIntegrityViolationException_quandoViolacaoDeRestricao() {
        Pedido pedido = populaValores();
        PedidoEntity pedidoEntity = new PedidoEntity(pedido);
        when(repository.save(any(PedidoEntity.class))).thenThrow(new RuntimeException("Referential integrity constraint violation"));

        assertThrows(DataIntegrityViolationException.class, () -> pedidoRepository.save(pedido));

    }

    @Test
    void save_deveLancarDataIntegrityViolationException_quandoErroGenerico() {
        Pedido pedido = populaValores();
        PedidoEntity pedidoEntity = new PedidoEntity(pedido);
        when(repository.save(pedidoEntity)).thenThrow(new RuntimeException("Erro genérico"));

        assertThrows(DataIntegrityViolationException.class, () -> pedidoRepository.save(pedido));
    }


    @Test
    void findById_deveRetornarPedido_quandoExiste() {

        Long id = 1L;
        Pedido pedido = populaValores();
        PedidoEntity pedidoEntity = new PedidoEntity(pedido);
        pedidoEntity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(pedidoEntity));

        pedidoRepository.findById(id);

        assertNotNull(pedido);
        assertEquals(id, pedido.getId());
    }

    @Test
    void findById_deveLancarNoSuchElementException_quandoNaoExiste() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pedidoRepository.findById(id));
        verify(repository).findById(id);
    }

    @Test
    void findAll_deveRetornarListaDePedidos() {
        Pedido pedido = populaValores();
        PedidoEntity pedidoEntity = new PedidoEntity(pedido);
        List<PedidoEntity> pedidoEntities = new ArrayList<>();
        pedidoEntities.add(pedidoEntity);
        when(repository.findNotFinalizedAndOrdered()).thenReturn(pedidoEntities);

        List<Pedido> pedidos = pedidoRepository.findAll();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        verify(repository).findNotFinalizedAndOrdered();
    }

    @Test
    void findAll_deveRetornarListaVaziaQuandoNaoHouverPedidos() {
        when(repository.findNotFinalizedAndOrdered()).thenReturn(new ArrayList<>());

        List<Pedido> pedidos = pedidoRepository.findAll();

        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());
        verify(repository).findNotFinalizedAndOrdered();
    }
}