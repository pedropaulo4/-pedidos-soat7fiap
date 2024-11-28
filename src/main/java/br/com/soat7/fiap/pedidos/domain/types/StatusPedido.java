package br.com.soat7.fiap.pedidos.domain.types;

import lombok.Getter;

@Getter
public enum StatusPedido {
    PRONTO("PRONTO",1),
    EM_PREPARACAO("EM PREPARACAO",2),
    RECEBIDO("RECEBIDO",3),
    FINALIZADO("FINALIZADO",4);

    private String value;
    private int order;

    StatusPedido(String value, int order) {
        this.value = value;
        this.order = order;
    }
}
