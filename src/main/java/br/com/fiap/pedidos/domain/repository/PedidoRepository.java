package br.com.fiap.pedidos.domain.repository;

import br.com.fiap.pedidos.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
