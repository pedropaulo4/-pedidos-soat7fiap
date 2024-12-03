package br.com.soat7.fiap.pedidos.infrastructure.controller.request;

import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoRequest {

    @JsonIgnore
    private Long id;

    private String nome;

    private Categoria categoria;

    BigDecimal preco;

    private String descricao;

    private String imagem;

}
