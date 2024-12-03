package br.com.soat7.fiap.pedidos.infrastructure.controller.response;

import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResponse {


    private Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private String descricao;
    private String imagem;
}
