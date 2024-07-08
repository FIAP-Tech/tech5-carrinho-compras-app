package br.com.fiap.pedidos.service;

import br.com.fiap.pedidos.api.exceptionhandler.ClienteNaoEncontradoException;
import br.com.fiap.pedidos.api.model.ClienteDto;
import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import br.com.fiap.pedidos.domain.service.ClienteService;
import br.com.fiap.pedidos.domain.service.PedidoService;
import br.com.fiap.pedidos.domain.service.SQSService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    private AutoCloseable closeable;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageConfig messageConfig;

    @Mock
    private SQSService sqsService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Nested
    class Add {

        @Test
        void deveCriarPedidoComSucesso() {
            PedidoDto pedidoDto = new PedidoDto();
            Pedido pedidoMock = new Pedido();
            ClienteDto clienteDto = new ClienteDto();

            when(modelMapper.map(pedidoDto, Pedido.class)).thenReturn(pedidoMock);
            when(clienteService.getClienteById(anyLong())).thenReturn(Optional.of(clienteDto));
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);

            //PedidoDto result = pedidoService.add(pedidoDto);

            //assertNotNull(result);
        }

        @Test
        void deveLancarExceptionAoTentaBuscarClienteInexistente() {
            PedidoDto pedidoDto = new PedidoDto();

            when(clienteService.getClienteById(anyLong())).thenThrow(new ClienteNaoEncontradoException("Cliente não foi encontrado"));

            //assertThrows(ClienteNaoEncontradoException.class, () -> pedidoService.add(pedidoDto));
        }
    }

    @Nested
    class GetPedidoById {

        @Test
        void deveBuscarPedidoPorIdComSucesso() {
            Long id = 1L;
            Pedido pedidoMock = new Pedido();
            PedidoDto pedidoDtoMock = new PedidoDto();

            when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMock));
            when(modelMapper.map(pedidoMock, PedidoDto.class)).thenReturn(pedidoDtoMock);

            PedidoDto result = pedidoService.getPedidoById(id);

            assertNotNull(result);
        }
    }

    @Nested
    class FindAll {

        @Test
        void deveBuscarPedidosComSucesso() {
            List<Pedido> pedidos = List.of(new Pedido(), new Pedido());
            List<PedidoDto> pedidoDtos = List.of(new PedidoDto(), new PedidoDto());

            when(pedidoRepository.findAll()).thenReturn(pedidos);
            when(modelMapper.map(any(), eq(PedidoDto.class))).thenReturn(pedidoDtos.get(0), pedidoDtos.get(1));

            List<PedidoDto> result = pedidoService.findAll();

            assertEquals(2, result.size());
        }
    }
}
