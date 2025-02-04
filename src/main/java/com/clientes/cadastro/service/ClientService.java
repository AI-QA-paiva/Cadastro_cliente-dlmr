package com.clientes.cadastro.service;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // TODO: Considere usar construtor para injeção de dependência ao invés de @Autowired para maior testabilidade e aderência ao princípio de inversão de dependência (DIP).
    // private final ClientRepository clientRepository;
    // public ClientService(ClientRepository clientRepository) {
    //     this.clientRepository = clientRepository;
    // }

    public Client registerClient(Client client) {
        // TODO: Adicionar validações antes de salvar o cliente, como verificar se o cliente já existe (por e-mail, documento, etc.).
        return clientRepository.save(client);
    }

    public List<Client> searchForClients() {
        // TODO: Adicionar paginação para evitar o retorno de uma lista muito grande, o que pode impactar a performance.
        return clientRepository.findAll();
    }

    public Optional<Client> getOnlyOneClient(Long id) {
        // TODO: Validar se o ID é nulo ou inválido antes de buscar no repositório.
        return clientRepository.findById(id);
    }

    public Optional<Client> getByName(String name) {
        // TODO: Avaliar se o método deve retornar uma lista ao invés de um Optional, caso o nome não seja único.
        return clientRepository.findByName(name);
    }

    public Client registeredClient(Client client) {
        // TODO: Este método parece duplicado com `registerClient`. Considere removê-lo ou renomeá-lo para algo mais específico.
        return clientRepository.save(client);
    }

    public void deleteCustomerRecord(Long id) {
        // TODO: Adicionar validação para verificar se o cliente existe antes de deletar, evitando erros desnecessários.
        clientRepository.deleteById(id);
    }

    // TODO: Caso o nome não seja único, descomente o método abaixo e remova o `getByName` para evitar confusão.
    // public List<Client> getByName(String name) {
    //     return clientRepository.findByName(name);
    // }
}