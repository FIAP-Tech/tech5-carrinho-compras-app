package br.com.fiap.pedidos.api.controller;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.service.PedidoService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    public List<PedidoDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PedidoDto getPedidoById(@PathVariable Long id) {
        return service.getPedidoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto add(@RequestBody @NotNull PedidoDto pedidoDto) {
        return service.add(pedidoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
