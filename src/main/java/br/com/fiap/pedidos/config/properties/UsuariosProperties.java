package br.com.fiap.pedidos.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UsuariosProperties {

    @Value("${api.usuario.endpoint}")
    private String endpoint;

    @Value("${api.usuario.retry.max-tentativas}")
    private Integer maxTentativas;

    @Value("${api.usuario.retry.duracao}")
    private Integer duracao;
}
