package br.com.fiap.pedidos.api.controller;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.enums.Status;
import br.com.fiap.pedidos.domain.service.PedidoService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER')")
    public List<PedidoDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER')")
    public PedidoDto getPedidoById(@PathVariable Long id) {
        return service.getPedidoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public PedidoDto add(@RequestBody @NotNull PedidoDto pedidoDto) {
        return service.add(pedidoDto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> atualizaStatusPedido(@PathVariable Long id, @RequestParam Status status) {
        return service.atualizaStatusPedido(id, status)
                .map(order -> ResponseEntity.ok("Pedido atualizado para o status: " + status))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
