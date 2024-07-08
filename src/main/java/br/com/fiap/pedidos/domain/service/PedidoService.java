package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.exceptionhandler.ClienteNaoEncontradoException;
import br.com.fiap.pedidos.api.exceptionhandler.ProdutoNaoEncontradoException;
import br.com.fiap.pedidos.api.model.*;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.domain.enums.Status;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.api.exceptionhandler.ProdutoNaoPossuiEstoqueException;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class PedidoService {


    private static final Logger logger = Logger.getLogger(PedidoService.class.getName());


    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    private final MessageConfig messageConfig;

    private final SQSService sqsService;

    private final ClienteService clienteService;

    private final ProdutoService produtoService;

    private void enviarEmailPedidoRecebido(Pedido pedido, ClienteDto cliente) {
        var mensagemEmailDto = new MensagemEmailDto();
        mensagemEmailDto.setEmailDestinatario(cliente.getEmail());
        mensagemEmailDto.setAssunto("Pedido recebido com Sucesso");
        mensagemEmailDto.criarCorpoEmailPedido(pedido, cliente.getNome());

        sqsService.enviarMensagemFilaEmail(mensagemEmailDto);
    }

    private void enviarPedidoParaProcessamento(Pedido pedido, ClienteDto cliente) {
        var pedidoProcessaDto = new PedidoProcessaDto();
        modelMapper.map(pedido, pedidoProcessaDto);
        modelMapper.map(cliente, pedidoProcessaDto);

        sqsService.enviarMensagemFilaPedidos(pedidoProcessaDto);
    }

    private void atualizarStatus(Pedido pedido) {
        pedido.setStatus(Status.VERIFICANDO_PAGAMENTO);
        pedidoRepository.save(pedido);
    }

    private void atualizarEstoqueProduto(Pedido pedido) {
        pedido.getPedidoProdutos()
                .forEach(pedidoProduto -> produtoService.atualizarEstoqueProduto(pedidoProduto.getId().getIdProduto(), pedidoProduto.getQuantidade()).subscribe());
    }

    private void processarPedido(Pedido pedido, ClienteDto cliente) {
        atualizarEstoqueProduto(pedido);
        enviarEmailPedidoRecebido(pedido, cliente);
        enviarPedidoParaProcessamento(pedido, cliente);
        atualizarStatus(pedido);
    }

    public PedidoDto add(PedidoDto pedidoDto) {
        var pedido = modelMapper.map(pedidoDto, Pedido.class);

        Optional<ClienteDto> clienteDto = clienteService.getClienteById(pedido.getIdCliente());

        if(clienteDto.isEmpty()) {
            throw new ClienteNaoEncontradoException(messageConfig.getClienteNaoEncontrado());
        }

        pedido.getPedidoProdutos()
                .forEach(pedidoProduto -> {
                    Optional<ProdutoDto> produtoDto = produtoService.getProdutoById(pedidoProduto.getId().getIdProduto());
                    logger.info("teste ricardo log: " + produtoDto);
                    if(produtoDto.isPresent()){
                        ProdutoDto produto = produtoDto.get();
                        if(produto.getEstoque() >= pedidoProduto.getQuantidade()) {
                            pedidoProduto.setDescricao(produto.getDescricao());
                            pedidoProduto.setPreco(produto.getPreco());
                        } else {
                            throw new ProdutoNaoPossuiEstoqueException(messageConfig.getProdutoNaoPossuiEstoque() + produto);
                        }
                    } else {
                        throw new ProdutoNaoEncontradoException(messageConfig.getProdutoNaoEncontrado() + pedidoProduto.getId().getIdProduto());
                    }
                });

        pedido = pedidoRepository.save(pedido);
        processarPedido(pedido, clienteDto.get());

        return modelMapper.map(pedido, PedidoDto.class);
    }

    @Transactional(readOnly = true)
    public PedidoDto getPedidoById(Long id) {
        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isPresent()){
            return modelMapper.map(optionalPedido.get(), PedidoDto.class);
        } else {
            throw new PedidoNaoEncontradoException(messageConfig.getPedidoNaoEncontrado());
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDto.class))
                .toList();
    }

    public Optional<Pedido> atualizaStatusPedido(Long orderId, Status novoStatus) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(orderId);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
            return Optional.of(pedido);
        }
        return Optional.empty();
    }
}
