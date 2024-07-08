package br.com.fiap.pedidos.api.client;

import br.com.fiap.pedidos.api.model.ProdutoDto;
import br.com.fiap.pedidos.config.properties.ProdutoProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoClient {

    private WebClient webClient;

    private final ProdutoProperties prop;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(prop.getEndpoint())
                .build();
    }

    public Mono<Optional<ProdutoDto>> getProdutoById(Long produtoId) {
        return this.webClient.get()
                .uri("/{id}", produtoId)
                .retrieve()
                .bodyToMono(ProdutoDto.class)
                .map(Optional::of)
                .retryWhen(Retry.fixedDelay(prop.getMaxTentativas(), Duration.ofSeconds(prop.getDuracao()))
                        .filter(WebClientResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.just(Optional.empty()))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Erro ao se comunicar com a API de produtos: " + ex.getMessage())));
    }

    public Mono<Optional<String>> atualizarEstoqueProduto(Long produtoId, Integer quantidadeVendida) {
        return this.webClient.post()
                .uri("/{id}/atualizar-estoque?quantidadeVendida={quantidade}", produtoId, quantidadeVendida)
                .retrieve()
                .bodyToMono(String.class)
                .map(Optional::of)
                .retryWhen(Retry.fixedDelay(prop.getMaxTentativas(), Duration.ofSeconds(prop.getDuracao()))
                        .filter(WebClientResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.just(Optional.empty()))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Erro ao se comunicar com a API de produtos: " + ex.getMessage())));
    }
}
