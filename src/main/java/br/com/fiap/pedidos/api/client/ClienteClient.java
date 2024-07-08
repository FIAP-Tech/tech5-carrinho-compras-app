package br.com.fiap.pedidos.api.client;

import br.com.fiap.pedidos.api.model.ClienteDto;
import br.com.fiap.pedidos.config.properties.ClienteProperties;
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
public class ClienteClient {

    private WebClient webClient;

    private final ClienteProperties prop;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(prop.getEndpoint())
                .build();
    }

    public Mono<Optional<ClienteDto>> getClienteById(Long clienteId) {
        return this.webClient.get()
                .uri("/{id}", clienteId)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .map(Optional::of)
                .retryWhen(Retry.fixedDelay(prop.getMaxTentativas(), Duration.ofSeconds(prop.getDuracao()))
                        .filter(WebClientResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.just(Optional.empty()))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Erro ao se comunicar com a API de clientes: " + ex.getMessage())));
    }
}
