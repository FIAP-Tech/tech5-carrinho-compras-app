package br.com.fiap.pedidos.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorMessage {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timestamp;

    private Integer status;

    private String message;

    private String path;

}
