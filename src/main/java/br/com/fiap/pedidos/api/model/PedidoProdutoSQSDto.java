package br.com.fiap.pedidos.api.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class PedidoProdutoSQSDto {

    private Long idProduto;

    private String descricao;

    private Integer quantidade;

    private BigDecimal preco;

}

