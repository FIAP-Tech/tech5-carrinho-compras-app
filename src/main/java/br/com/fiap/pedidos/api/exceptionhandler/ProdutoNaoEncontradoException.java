package br.com.fiap.pedidos.api.exceptionhandler;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}