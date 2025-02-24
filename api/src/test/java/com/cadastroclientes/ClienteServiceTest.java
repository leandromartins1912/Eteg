package com.cadastroclientes;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cadastroclientes.dto.ClienteDTO;
import com.cadastroclientes.exception.ValidationException;
import com.cadastroclientes.model.Cliente;
import com.cadastroclientes.repository.ClienteRepository;
import com.cadastroclientes.service.ClienteService;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClienteServiceTest {
	@Mock
    private ClienteRepository clienteRepository; 

    @InjectMocks
    private ClienteService clienteService; 

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
    	clienteDTO = new ClienteDTO("Leandro", "leandro@email.com", "Branco", "12345678901", "Observacao");
    }

    @Test
    void testCriarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao"));

        Cliente cliente = clienteService.criarCliente(clienteDTO);

        assertNotNull(cliente);
        assertEquals("Leandro", cliente.getNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class)); 
    }

    @Test
    void testCriarClienteComCpfExistente() {
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(new Cliente(1L, "Outro Cliente", "outro@email.com", "12345678901", "Azul", "Observacao")));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clienteService.criarCliente(clienteDTO);
        });

        assertEquals("Já existe um cliente cadastrado com este CPF.", exception.getMessage());
    }

    @Test
    void testAtualizarCliente() {
        Cliente clienteExistente = new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");
        ClienteDTO clienteAtualizadoDTO = new ClienteDTO("Leandro Silva", "leandro@email.com", "12345678901", "Branco", "Nova Observacao");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);

        Cliente clienteAtualizado = clienteService.atualizarCliente(1L, clienteAtualizadoDTO);

        assertNotNull(clienteAtualizado);
        assertEquals("Leandro Silva", clienteAtualizado.getNome());
        assertEquals("Nova Observacao", clienteAtualizado.getObservacao());
    }

    @Test
    void testAtualizarClienteNaoEncontrado() {        
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clienteService.atualizarCliente(1L, clienteDTO);
        });

        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    void testDeletarCliente() {
        Cliente clienteExistente = new Cliente(1L, "Leandro", "leandro@email.com", "12345678901", "Branco", "Observacao");

       
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        
        clienteService.deletarCliente(1L);

        
        verify(clienteRepository, times(1)).delete(clienteExistente);
    }

    @Test
    void testDeletarClienteNaoEncontrado() {
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clienteService.deletarCliente(1L);
        });

        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    void testValidarClienteComEmailExistente() {
        
        when(clienteRepository.findByEmail("leandro@email.com")).thenReturn(Optional.of(new Cliente(1L, "Outro Cliente", "leandro@email.com", "12345678901", "Branco", "Observacao")));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clienteService.criarCliente(clienteDTO);
        });

        assertEquals("Já existe um cliente cadastrado com este e-mail.", exception.getMessage());
    }
}
