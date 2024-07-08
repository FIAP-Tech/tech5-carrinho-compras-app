package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.client.ProdutoClient;
import br.com.fiap.pedidos.api.model.ProdutoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoClient client;

    public Optional<ProdutoDto> getProdutoById(Long produtoId) {
        return client.getProdutoById(produtoId).block();
    }

    public Mono<Optional<String>> atualizarEstoqueProduto(Long produtoId, Integer quantidadeVendida) {
        return client.atualizarEstoqueProduto(produtoId, quantidadeVendida);
    }
}

