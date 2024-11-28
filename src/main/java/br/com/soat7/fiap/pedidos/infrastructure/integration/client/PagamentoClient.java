package br.com.soat7.fiap.pedidos.infrastructure.integration.client;

import br.com.soat7.fiap.pedidos.domain.Pedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamentos", url = "${pagamentos.url}")
public interface PagamentoClient {

    @PostMapping(value = "/pagamentos", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Pedido> efetuarPagamento(@RequestBody Pedido pedido);

}
