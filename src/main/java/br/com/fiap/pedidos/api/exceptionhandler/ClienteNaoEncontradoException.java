package br.com.fiap.pedidos.api.exceptionhandler;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}