package br.com.fiap.pedidos.domain.model;

import br.com.fiap.pedidos.domain.model.embedded.PedidoProdutoId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PedidoProduto {

    @EmbeddedId
    private PedidoProdutoId id;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    private String descricao;

    private Integer quantidade;

    private BigDecimal preco;

}

