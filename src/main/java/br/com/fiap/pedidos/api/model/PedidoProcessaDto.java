package br.com.fiap.pedidos.api.model;

import br.com.fiap.pedidos.domain.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProcessaDto {

    private Long idPedido;

    private ClienteDto cliente;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataPedido;

    private BigDecimal totalPedido;

    private FormaPagamento formaPagamento;

    private String validadeFormaPagamentoCartao;

    private List<PedidoProdutoSQSDto> pedidoProdutos;
}
