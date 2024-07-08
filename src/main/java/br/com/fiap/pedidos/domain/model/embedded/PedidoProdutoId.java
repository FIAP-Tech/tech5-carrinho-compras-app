package br.com.fiap.pedidos.domain.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PedidoProdutoId implements Serializable {

    private Long idPedido;

    private Long idProduto;

}

