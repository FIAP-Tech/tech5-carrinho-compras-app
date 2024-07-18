package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.client.UsuarioClient;
import br.com.fiap.pedidos.api.model.UsuarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient client;

    public Optional<UsuarioDto> getClienteById(Long clienteId) {
        return client.getUsuarioById(clienteId).block();
    }
}

