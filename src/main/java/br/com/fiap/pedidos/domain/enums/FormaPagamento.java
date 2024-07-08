package br.com.fiap.pedidos.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum FormaPagamento {

    BOLETO,
    CARTAO_DEBITO,
    CARTAO_CREDITO;


    @JsonCreator
    public static FormaPagamento forValue(String value) {
        return Stream.of(FormaPagamento.values())
                .filter(enumValue -> enumValue.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Forma de pagamento inválida: " + value));
    }
}
