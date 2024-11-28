package br.com.soat7.fiap.pedidos.infrastructure.controller;

import br.com.soat7.fiap.pedidos.application.usecases.*;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.domain.types.StatusPagamento;
import br.com.soat7.fiap.pedidos.domain.types.StatusPedido;
import br.com.soat7.fiap.pedidos.infrastructure.controller.request.PedidoRequest;
import br.com.soat7.fiap.pedidos.infrastructure.controller.response.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private AdicionarPedidoUsecase adicionarPedidoUsecase;
    @Mock
    private MarcarPedidoProntoUsecase marcarPedidoProntoUsecase;
    @Mock
    private PrepararPedidoUsecase prepararPedidoUsecase;
    @Mock
    private FinalizarPedidoUsecase finalizarPedidoUsecase;
    @Mock
    private BuscarTodosPedidosUsecase buscarTodosPedidosUsecase;
    @Mock
    private ConsultarStatusPagamentoPedidoUsecase consultarStatusPagamentoPedidoUsecase;

    private ModelMapper modelMapper;

    private PedidoController pedidoController;

    private Pedido pedido;
    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<List<Long>, List<Produto>> idToProdutoConverter = new AbstractConverter<>() {
            protected List<Produto> convert(List<Long> source) {
                return source.stream().map(Produto::new).toList();
            }
        };
        modelMapper.typeMap(PedidoRequest.class, Pedido.class).addMappings(mapper ->
                mapper.using(idToProdutoConverter).map(PedidoRequest::getIdProdutoList, Pedido::setProdutoList)
        );
        pedidoController = new PedidoController(adicionarPedidoUsecase, marcarPedidoProntoUsecase, prepararPedidoUsecase,
                finalizarPedidoUsecase, buscarTodosPedidosUsecase, modelMapper, consultarStatusPagamentoPedidoUsecase);
        pedido = new Pedido();
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

        List<Long> listRequest = new ArrayList<>();
        listRequest.add(Long.valueOf(1));
        pedidoRequest = new PedidoRequest();
        pedidoRequest.setCpf("70156056110");
        pedidoRequest.setDataCadastro(Date.valueOf(LocalDate.now()));
        pedidoRequest.setIdProdutoList(listRequest);
        pedidoRequest.setObservacao("Teste");
        pedidoRequest.setStatus(StatusPedido.RECEBIDO);


        pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(1L);
        pedidoResponse.setId(1L);
        pedidoResponse.setCpf("70156056110");
        pedidoResponse.setDataCadastro(Date.valueOf(LocalDate.now()));
        pedidoResponse.setStatusPagamento(StatusPagamento.APROVADO);
        pedidoResponse.setProdutoList(produtos);
        pedidoResponse.setObservacao("Teste");
        pedidoResponse.setStatus(StatusPedido.RECEBIDO);

    }


    @Test
    void adicionarPedido_deveRetornarPedidoResponse_quandoSucesso() throws Exception{
        when(adicionarPedidoUsecase.criarPedido(any(Pedido.class))).thenReturn(pedido);
        ResponseEntity<PedidoResponse> response = pedidoController.adicionarPedido(pedidoRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(pedidoResponse.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void marcarComoPronto_deveRetornarPedidoResponse_quandoSucesso() {
        Long id = 1L;
        when(marcarPedidoProntoUsecase.marcarComoPronto(id)).thenReturn(pedido);
        ResponseEntity<PedidoResponse> response = pedidoController.marcarComoPronto(id);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(pedidoResponse.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());
    }


    @Test
    void buscarPedidos_deveRetornarListaDePedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);
        when(buscarTodosPedidosUsecase.buscarPedidos()).thenReturn(pedidos);
        ResponseEntity<List<Pedido>> response = pedidoController.buscarPedidos();
        assertNotNull(response);
        assertEquals(pedidos, response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void prepararPedido_deveRetornarPedidoResponse_quandoSucesso() {
        Long id = 1L;
        when(prepararPedidoUsecase.prepararPedido(id)).thenReturn(pedido);
        ResponseEntity<PedidoResponse> response = pedidoController.prepararPedido(id);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(pedidoResponse.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void finalizarPedido_deveRetornarPedidoResponse_quandoSucesso() {
        Long id = 1L;
        when(finalizarPedidoUsecase.finalizarPedido(id)).thenReturn(pedido);
        ResponseEntity<PedidoResponse> response = pedidoController.finalizarPedido(id);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(pedidoResponse.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void consultarStatusPagamento_deveRetornarPedidoResponse_quandoSucesso() throws Exception {
        Long id = 1L;
        when(consultarStatusPagamentoPedidoUsecase.consultarStatusPagamentoPedido(id)).thenReturn(pedido);
        ResponseEntity<PedidoResponse> response = pedidoController.consultarStatusPagamentoPedido(id);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(pedidoResponse.getId(), response.getBody().getId());
        assertEquals(200, response.getStatusCode().value());
    }

}