package com.clientes.cadastro.service;

import com.clientes.cadastro.exception.BadRequest;
import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    //comportamentos
    public Client registerClient(Client client) throws Exception{
        Optional<Client> nameClientOpt = clientRepository.findByEmail(client.getEmail());
        Optional<Client> emailClientOpt = clientRepository.findByEmail(client.getEmail());
        Optional<Client> ssrClientOpt = clientRepository.findByDocument(client.getDocument());

        if(emailClientOpt.isPresent()){
            throw new BadRequest("Email already exists");
        }
        if(ssrClientOpt.isPresent()){
            throw new BadRequest("CPF already exists");
        }
        return clientRepository.save(client);
    }

    public List<Client> searchForClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getOnlyOneClient(Long id) {

        Optional<Client> findClient = clientRepository.findById(id);
        if(!findClient.isPresent()){
            throw new BadRequest("Cliente com o Id " + id + " não foi encontrado.");
        }
        return clientRepository.findById(id);
    }

    public List<Client> searchByNameParts(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    public Client updateClientId(Client client) {
        return clientRepository.save(client);
    }

    public void deleteCustomerRecord(Long id) {
        Optional<Client> findClient = clientRepository.findById(id);
        if(!findClient.isPresent()){
            throw new BadRequest("Cliente com o Id " + id + " não foi encontrado.");
        }
        clientRepository.deleteById(id);
    }

}