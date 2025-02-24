package com.cadastroclientes.controller;

import com.cadastroclientes.dto.ClienteDTO;
import com.cadastroclientes.exception.ValidationException;
import com.cadastroclientes.model.Cliente;
import com.cadastroclientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	@Autowired
    private ClienteService clienteService;

	@Operation(summary = "Obter lista de usuários", 
            description = "Retorna todos os usuários cadastrados.")
	@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
	@GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

	@Operation(summary = "Obter cliente por ID", description = "Retorna um cliente com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }

	@Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody @Validated ClienteDTO clienteDTO) {
        try {
            Cliente clienteCriado = clienteService.criarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody @Validated ClienteDTO clienteDTO) {
        try {
            Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@Operation(summary = "Deletar cliente", description = "Deleta um cliente existente com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }
}
