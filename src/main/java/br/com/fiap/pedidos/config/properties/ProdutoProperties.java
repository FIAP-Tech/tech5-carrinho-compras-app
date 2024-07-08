package br.com.fiap.pedidos.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ProdutoProperties {

    @Value("${api.produto.endpoint}")
    private String endpoint;

    @Value("${api.produto.retry.max-tentativas}")
    private Integer maxTentativas;

    @Value("${api.produto.retry.duracao}")
    private Integer duracao;
}
