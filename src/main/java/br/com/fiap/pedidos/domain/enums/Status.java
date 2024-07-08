package br.com.fiap.pedidos.domain.enums;


public enum Status {

    NOVO,
    VERIFICANDO_PAGAMENTO,
    PAGAMENTO_EFETUADO,
    FALHA_NO_PAGAMENTO,
    EM_ROTA_DE_ENTREGA,
    FALHA_NA_ENTREGA,
    ENTREGUE;

}
