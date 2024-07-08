package br.com.fiap.pedidos.service;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import br.com.fiap.pedidos.domain.service.PedidoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class PedidoServiceIntegrationTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Nested
    class adicionarPedido {

        //@Test
        void deveAdicionarPedido_ComSucesso() {
            var pedidoDto = new PedidoDto();
            pedidoDto.setIdPedido(null);

            pedidoService.add(pedidoDto);
            var listaPedidos = pedidoService.findAll();

            assertThat(listaPedidos)
                    .isNotNull()
                    .hasSize(1);
            assertThat(listaPedidos.get(0))
                    .isNotNull()
                    .isInstanceOf(PedidoDto.class);
            //assertThat(listaPedidos.get(0).getEmail())
                    //.isEqualTo(pedidoDto.getEmail());
        }
    }

    @Nested
    class buscarPedidoPorId {

        @Test
        void deveBuscarPedidoPorId_ComSucesso() {
            var id = 1L;

            var pedido = pedidoService.getPedidoById(id);

            assertThat(pedido)
                    .isNotNull()
                    .isInstanceOf(PedidoDto.class);
//            assertThat(pedido.getEmail())
//                    .isEqualTo("ricardo@gmail.com");
//            assertThat(pedido.getNome())
//                    .isEqualTo("Ricardo Silva");
        }

        @Test
        void deveLancarExceptionAoTentarEncontrarUsuarioQueNaoExiste() {
            var id = 999L;

            assertThatThrownBy(() -> pedidoService.getPedidoById(id))
                    .isInstanceOf(PedidoNaoEncontradoException.class)
                    .hasMessage("Pedido não foi encontrado");
        }
    }

    @Nested
    class buscarPedidos {

        //@Test
        void deveBuscarTodosPedidos_ComSucesso() {
            var listaPedidos = pedidoService.findAll();

            assertThat(listaPedidos)
                    .isNotNull()
                    .hasSize(2);
            assertThat(listaPedidos.get(0))
                    .isNotNull()
                    .isInstanceOf(PedidoDto.class);
        }
    }
}
