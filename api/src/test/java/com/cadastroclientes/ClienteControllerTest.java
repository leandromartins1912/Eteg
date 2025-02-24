package com.cadastroclientes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cadastroclientes.controller.ClienteController;
import com.cadastroclientes.dto.ClienteDTO;
import com.cadastroclientes.exception.ValidationException;
import com.cadastroclientes.model.Cliente;
import com.cadastroclientes.service.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testListarClientes() throws Exception {
        Cliente cliente = new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");
        when(clienteService.listarTodos()).thenReturn(Arrays.asList(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Leandro"));
    }

    @Test
    void testBuscarClienteExistente() throws Exception {
        Cliente cliente = new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");
        when(clienteService.buscarPorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Leandro"));
    }

    @Test
    void testBuscarClienteInexistente() throws Exception {
        when(clienteService.buscarPorId(1L)).thenThrow(new ValidationException("Cliente não encontrado"));

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("Cliente não encontrado"));
    }

    @Test
    void testCriarClienteComSucesso() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");
        Cliente cliente = new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");
        when(clienteService.criarCliente(any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Leandro"));
    }

    @Test
    void testAtualizarClienteComSucesso() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("Leandro Atualizado", "leandro@email.com", "12345678901", "Azul", "Nova Observacao");
        Cliente cliente = new Cliente(1L, "Leandro Atualizado", "leandro@email.com", "12345678901", "Azul", "Nova Observacao");
        when(clienteService.atualizarCliente(eq(1L), any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Leandro Atualizado"));
    }

    @Test
    void testDeletarClienteComSucesso() throws Exception {
        doNothing().when(clienteService).deletarCliente(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }
}

