package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.client.ClienteClient;
import br.com.fiap.pedidos.api.model.ClienteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteClient client;

    public Optional<ClienteDto> getClienteById(Long clienteId) {
        return client.getClienteById(clienteId).block();
    }
}

