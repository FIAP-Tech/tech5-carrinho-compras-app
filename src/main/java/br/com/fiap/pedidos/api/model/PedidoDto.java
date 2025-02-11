package br.com.fiap.pedidos.api.model;

import br.com.fiap.pedidos.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private Long idPedido;

    private Long idUsuario;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double totalPedido;

    private List<PedidoProdutoDto> pedidoProdutos;
}
