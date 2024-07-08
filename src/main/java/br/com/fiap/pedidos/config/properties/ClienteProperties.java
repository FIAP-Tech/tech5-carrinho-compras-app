package br.com.fiap.pedidos.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClienteProperties {

    @Value("${api.cliente.endpoint}")
    private String endpoint;

    @Value("${api.cliente.retry.max-tentativas}")
    private Integer maxTentativas;

    @Value("${api.cliente.retry.duracao}")
    private Integer duracao;
}
