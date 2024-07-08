package br.com.fiap.pedidos.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private EnderecoDto endereco;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String cpf;
}
