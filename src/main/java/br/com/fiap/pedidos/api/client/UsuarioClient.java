package br.com.fiap.pedidos.api.client;

import br.com.fiap.pedidos.api.model.UsuarioDto;
import br.com.fiap.pedidos.config.properties.UsuariosProperties;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private WebClient webClient;

    private final UsuariosProperties prop;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(prop.getEndpoint())
                .build();
    }


    public Mono<Optional<UsuarioDto>> getUsuarioById(Long usuarioId) {
        String token = extractTokenFromRequest((HttpServletRequest) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return this.webClient.get()
                .uri("/{id}", usuarioId)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(UsuarioDto.class)
                .map(Optional::of)
                .retryWhen(Retry.fixedDelay(prop.getMaxTentativas(), Duration.ofSeconds(prop.getDuracao()))
                        .filter(WebClientResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.just(Optional.empty()))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Erro ao se comunicar com a API de usuários: " + ex.getMessage())));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
