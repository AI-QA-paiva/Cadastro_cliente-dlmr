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

    public Client registerClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> searchForClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getOnlyOneClient(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getByName(String name) {
        return clientRepository.findByName(name);
    }

    public Client registeredClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteCustomerRecord(Long id) {
        clientRepository.deleteById(id);
    }


}

/* ESTUDAR O CASO: >> com pesquisar por nome que pode me trzer uma lista porque tem várias pessoas com um nome em comum???*/
//public List<Client> getByName(String name) {
//    return clientRepository.findByName(name);
//}