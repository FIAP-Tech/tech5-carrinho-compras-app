package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.model.PedidoProcessaDto;
import br.com.fiap.pedidos.domain.exception.ErroConverterObjetoParaJsonException;
import br.com.fiap.pedidos.api.model.MensagemEmailDto;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SQSService {

    @Value("${aws.sqs.endpoint.envia-emails}")
    private String sqsEndpointEnviaEmail;

    @Value("${aws.sqs.endpoint.processa-pedidos}")
    private String sqsEndpointProcessaPedidos;

    private final AmazonSQS sqsClient;

    private final ObjectMapper objectMapper;

    public SQSService(AmazonSQS amazonSQS, ObjectMapper objectMapper) {
        this.sqsClient = amazonSQS;
        this.objectMapper = objectMapper;
    }

    public void enviarMensagemFilaEmail(MensagemEmailDto mensagem) {
        SendMessageRequest sendMessageRequest = null;

        try {
            sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(sqsEndpointEnviaEmail)
                    .withMessageBody(objectMapper.writeValueAsString(mensagem));
            sqsClient.sendMessage(sendMessageRequest);
        } catch (JsonProcessingException e) {
            throw new ErroConverterObjetoParaJsonException("Erro ao converter MensagemEmailDto para json");
        }
    }

    public void enviarMensagemFilaPedidos(PedidoProcessaDto pedidoProcessa) {
        SendMessageRequest sendMessageRequest = null;

        try {
            sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(sqsEndpointProcessaPedidos)
                    .withMessageBody(objectMapper.writeValueAsString(pedidoProcessa));
            sqsClient.sendMessage(sendMessageRequest);
        } catch (JsonProcessingException e) {
            throw new ErroConverterObjetoParaJsonException("Erro ao converter PedidoProcessaDto para json");
        }
    }
}
