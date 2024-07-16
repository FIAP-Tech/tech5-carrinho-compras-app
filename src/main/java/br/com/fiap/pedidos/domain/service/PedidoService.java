package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.exceptionhandler.UsuarioNaoEncontradoException;
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

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    private final MessageConfig messageConfig;

    private final UsuarioService usuarioService;

    private final ProdutoService produtoService;

    private void atualizarStatus(Pedido pedido) {
        pedido.setStatus(Status.VERIFICANDO_PAGAMENTO);
        pedidoRepository.save(pedido);
    }

    private void atualizarEstoqueProduto(Pedido pedido) {
        pedido.getPedidoProdutos()
                .forEach(pedidoProduto -> produtoService.atualizarEstoqueProduto(pedidoProduto.getId().getIdProduto(), pedidoProduto.getQuantidade()).subscribe());
    }

    private void processarPedido(Pedido pedido, UsuarioDto cliente) {
        atualizarEstoqueProduto(pedido);
        atualizarStatus(pedido);
    }

    public PedidoDto add(PedidoDto pedidoDto) {
        var pedido = modelMapper.map(pedidoDto, Pedido.class);

        Optional<UsuarioDto> usuarioDto = usuarioService.getClienteById(pedido.getIdUsuario());

        if(usuarioDto.isEmpty()) {
            throw new UsuarioNaoEncontradoException(messageConfig.getUsuarioNaoEncontrado());
        }

        pedido.getPedidoProdutos()
                .forEach(pedidoProduto -> {
                    Optional<ProdutoDto> produtoDto = produtoService.getProdutoById(pedidoProduto.getId().getIdProduto());
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
        processarPedido(pedido, usuarioDto.get());

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

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }
}
