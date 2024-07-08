package br.com.fiap.pedidos.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {

    private Long id;

    @JsonProperty("name")
    private String descricao;

    @JsonProperty("quantity")
    private Integer estoque;

    @JsonProperty("price")
    private BigDecimal preco;
}
