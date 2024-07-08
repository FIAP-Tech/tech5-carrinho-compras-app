package br.com.fiap.pedidos.domain.exception;

public class ErroConverterObjetoParaJsonException extends RuntimeException{

    public ErroConverterObjetoParaJsonException(String mensagem) {
        super(mensagem);
    }
}
