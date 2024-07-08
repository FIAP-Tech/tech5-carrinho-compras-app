DELETE FROM pedido_produto;
DELETE FROM pedido;

INSERT INTO pedido (id_pedido, data_pedido, forma_pagamento, id_cliente, status, total_pedido)
VALUES (1, '2024-05-16 06:30', 'BOLETO', 1, 'VERIFICANDO_PAGAMENTO', 5558.50),
       (2, '2024-05-16 12:15', 'BOLETO', 1,'VERIFICANDO_PAGAMENTO', 5558.50),
       (3, '2024-05-16 19:00', 'CARTAO_CREDITO', 1, 'VERIFICANDO_PAGAMENTO', 5558.50);

INSERT INTO pedido_produto (id_produto, preco, quantidade, id_pedido, descricao)
VALUES (1, 5500.00, 1, 1, 'Iphone 13'),
	   (2, 23.50, 1, 1, 'MacbookPro M1'),
	   (3, 3.50, 10, 1, 'Capa de Celular'),
	   (1, 5500.00, 1, 2, 'Iphone 13'),
	   (2, 23.50, 1, 2, 'MacbookPro M1'),
	   (3, 3.50, 10, 2, 'Capa de Celular'),
	   (1, 5500.00, 1, 3, 'Iphone 13'),
	   (2, 23.50, 1, 3, 'MacbookPro M1'),
	   (3, 3.50, 10, 3, 'Capa de Celular');