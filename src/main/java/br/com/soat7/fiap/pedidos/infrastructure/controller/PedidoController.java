package br.com.soat7.fiap.pedidos.infrastructure.controller;

import br.com.soat7.fiap.pedidos.application.usecases.*;
import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.infrastructure.controller.request.PedidoRequest;
import br.com.soat7.fiap.pedidos.infrastructure.controller.response.PedidoResponse;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/pedidos")
public class PedidoController {

    private final AdicionarPedidoUsecase adicionarPedidoUsecase;
    private final MarcarPedidoProntoUsecase marcarPedidoProntoUsecase;

    private final PrepararPedidoUsecase prepararPedidoUsecase;

    private final FinalizarPedidoUsecase finalizarPedidoUsecase;

    private final BuscarTodosPedidosUsecase buscarTodosPedidosUsecase;

    private final ConsultarStatusPagamentoPedidoUsecase consultarStatusPagamentoPedidoUsecase;

    private final ModelMapper modelMapper;


    public PedidoController(AdicionarPedidoUsecase adicionarPedidoUsecase,
                            MarcarPedidoProntoUsecase marcarPedidoProntoUsecase,
                            PrepararPedidoUsecase prepararPedidoUsecase,
                            FinalizarPedidoUsecase finalizarPedidoUsecase,
                            BuscarTodosPedidosUsecase buscarTodosPedidosUsecase,
                            ModelMapper modelMapper,
                            ConsultarStatusPagamentoPedidoUsecase consultarStatusPagamentoPedidoUsecase) {
        this.adicionarPedidoUsecase = adicionarPedidoUsecase;
        this.marcarPedidoProntoUsecase = marcarPedidoProntoUsecase;
        this.prepararPedidoUsecase = prepararPedidoUsecase;
        this.finalizarPedidoUsecase = finalizarPedidoUsecase;
        this.buscarTodosPedidosUsecase = buscarTodosPedidosUsecase;
        this.consultarStatusPagamentoPedidoUsecase = consultarStatusPagamentoPedidoUsecase;
        this.modelMapper = modelMapper;
        setupModelMapper(this.modelMapper);
    }

    @PostMapping("/checkout")
    public ResponseEntity<PedidoResponse> adicionarPedido(@RequestBody PedidoRequest request) throws Exception {
        Pedido pedidoRequest = modelMapper.map(request, Pedido.class);
        PedidoResponse response = modelMapper.map(adicionarPedidoUsecase.criarPedido(pedidoRequest), PedidoResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}/pronto")
    public ResponseEntity<PedidoResponse> marcarComoPronto(@PathVariable Long id) {
        Pedido pedido = marcarPedidoProntoUsecase.marcarComoPronto(id);
        PedidoResponse response = modelMapper.map(pedido, PedidoResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}/preparar")
    public ResponseEntity<PedidoResponse> prepararPedido(@PathVariable Long id) {
        Pedido pedido = prepararPedidoUsecase.prepararPedido(id);
        PedidoResponse response = modelMapper.map(pedido, PedidoResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<PedidoResponse> finalizarPedido(@PathVariable Long id) {
        Pedido pedido = finalizarPedidoUsecase.finalizarPedido(id);
        PedidoResponse response = modelMapper.map(pedido, PedidoResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<Pedido>> buscarPedidos(){
        return ResponseEntity.ok().body(buscarTodosPedidosUsecase.buscarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> consultarStatusPagamentoPedido(@PathVariable Long id) throws Exception {
        Pedido pedido = consultarStatusPagamentoPedidoUsecase.consultarStatusPagamentoPedido(id);
        return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoResponse.class));
    }

    private void setupModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<List<Long>, List<Produto>> idToProdutoConverter = new AbstractConverter<>() {
            protected List<Produto> convert(List<Long> source) {
                return source.stream().map(Produto::new).toList();
            }
        };
        modelMapper.typeMap(PedidoRequest.class, Pedido.class).addMappings(mapper ->
                mapper.using(idToProdutoConverter).map(PedidoRequest::getIdProdutoList, Pedido::setProdutoList)
        );
    }

}
