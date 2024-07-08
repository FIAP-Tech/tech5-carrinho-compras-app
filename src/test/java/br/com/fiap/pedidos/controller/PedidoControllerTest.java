package br.com.fiap.pedidos.controller;

import br.com.fiap.pedidos.api.controller.PedidoController;
import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveBuscarTodosPedidosComSucesso() throws Exception {
        List<PedidoDto> pedidos = Arrays.asList(new PedidoDto(), new PedidoDto());

        when(pedidoService.findAll()).thenReturn(pedidos);

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(pedidos.size()));

        verify(pedidoService, times(1)).findAll();
    }

    @Test
    void deveBuscarPedidoPorIdComSucesso() throws Exception {
        var id = 1L;
        var pedidoDto = new PedidoDto();

        when(pedidoService.getPedidoById(id)).thenReturn(pedidoDto);

        mockMvc.perform(get("/api/pedidos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoDto)));

        verify(pedidoService, times(1)).getPedidoById(id);
    }

    //@Test
    void deveAdicionarPedidoComSucesso() throws Exception {
        var pedidoDto = new PedidoDto();

        doNothing().when(pedidoService).add(pedidoDto);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDto)))
                .andExpect(status().isCreated());

        verify(pedidoService, times(1)).add(pedidoDto);
    }

}
