package br.com.fiap.pedidos.api.exceptionhandler;

public class ProdutoNaoPossuiEstoqueException extends RuntimeException {
    public ProdutoNaoPossuiEstoqueException(String mensagem) {
        super(mensagem);
    }
}