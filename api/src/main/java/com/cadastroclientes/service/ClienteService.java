package com.cadastroclientes.service;


import com.cadastroclientes.dto.ClienteDTO;
import com.cadastroclientes.exception.ValidationException;
import com.cadastroclientes.model.Cliente;
import com.cadastroclientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Cliente não encontrado com ID: " + id));
    }

    @Transactional
    public Cliente criarCliente(ClienteDTO clienteDTO) {
    	validarCliente(clienteDTO, null);
    	
        Cliente cliente = new Cliente(
            null,
            clienteDTO.getNome(),
            clienteDTO.getEmail(),
            clienteDTO.getCpf(),
            clienteDTO.getCor(),
            clienteDTO.getObservacao()
        );

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = buscarPorId(id);
        validarCliente(clienteDTO, id);

        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());
        clienteExistente.setCpf(clienteDTO.getCpf());
        clienteExistente.setCor(clienteDTO.getCor());
        clienteExistente.setObservacao(clienteDTO.getObservacao());

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void deletarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

    private void validarCliente(ClienteDTO clienteDTO, Long id) {
    	Optional<Cliente> clienteComMesmoCpf = clienteRepository.findByCpf(clienteDTO.getCpf());
    	Optional<Cliente> clienteComMesmoEmail = clienteRepository.findByEmail(clienteDTO.getEmail());
    	
    	if (clienteComMesmoCpf.isPresent()) {
            Cliente clienteEncontrado = clienteComMesmoCpf.get();
            if (!clienteEncontrado.getId().equals(id)) {
                throw new ValidationException("Já existe um cliente cadastrado com este CPF.");
            }
        }
    	
        if (clienteDTO.getNome() == null || clienteDTO.getNome().isEmpty()) {
            throw new ValidationException("O nome é obrigatório.");
        }
        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty()) {
            throw new ValidationException("O e-mail é obrigatório.");
        }
        if (clienteDTO.getCpf() == null || clienteDTO.getCpf().isEmpty()) {
            throw new ValidationException("O CPF é obrigatório.");
        }
        if (clienteComMesmoEmail.isPresent() && !clienteComMesmoEmail.get().getId().equals(id)) {
            throw new ValidationException("Já existe um cliente cadastrado com este e-mail.");
        }
        
    }
}
