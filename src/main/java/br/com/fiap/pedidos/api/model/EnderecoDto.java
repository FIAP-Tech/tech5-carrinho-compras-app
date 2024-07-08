package br.com.fiap.pedidos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {

    private String logradouro;

    private String bairro;

    private String estado;

    private String cidade;

    private String cep;

}