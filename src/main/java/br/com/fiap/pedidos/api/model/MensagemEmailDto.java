package br.com.fiap.pedidos.api.model;

import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.model.PedidoProduto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MensagemEmailDto {

    private String emailDestinatario;

    private String assunto;

    private String corpoEmail;

    public void criarCorpoEmailPedido(Pedido pedido, String nomeCliente) {
        StringBuilder produtosHtml = new StringBuilder();

        for (PedidoProduto pedidoProduto : pedido.getPedidoProdutos()) {
            produtosHtml.append("<tr>")
                    .append("<td>").append(pedidoProduto.getDescricao()).append("</td>")
                    .append("<td>R$ ").append(pedidoProduto.getPreco()).append("</td>")
                    .append("<td>").append(pedidoProduto.getQuantidade()).append("</td>")
                    .append("<td>R$ ").append(pedidoProduto.getPreco().multiply(BigDecimal.valueOf(pedidoProduto.getQuantidade()))).append("</td>")
                    .append("</tr>");
        }

        this.setCorpoEmail("<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Pedido Efetuado com Sucesso - FIAP E-commerce</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #F8F8F8;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #FFFFFF;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1, h2, p {\n" +
                "            margin-top: 0;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #00215E;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 8px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Pedido Efetuado com Sucesso!</h1>\n" +
                "        <p>Olá " + nomeCliente + ",</p>\n" +
                "        <p>Seu pedido foi efetuado com sucesso! Abaixo estão os detalhes do seu pedido:</p>\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>Produto</th>\n" +
                "                <th>Preço</th>\n" +
                "                <th>Quantidade</th>\n" +
                "                <th>Total</th>\n" +
                "            </tr>\n" +
                                produtosHtml +
                "            <tr>\n" +
                "                <td colspan=\"3\" style=\"text-align: right;\"><strong>Total do Pedido:</strong></td>\n" +
                "                <td><strong>R$ " + pedido.getTotalPedido() + "</strong></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <p>Agradecemos por comprar conosco!</p>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Atenciosamente,<br>Equipe FIAP E-commerce</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }

}
