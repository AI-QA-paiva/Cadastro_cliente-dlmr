package com.clientes.cadastro.repository;

import com.clientes.cadastro.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.List;
import java.util.Optional;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static com.clientes.cadastro.common.ClientConstants.CLIENT2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach(){
        CLIENT.setId(null);
    }

    @Test
    public void createClientWithValidData(){

        Client client = clientRepository.save(CLIENT);

        //alvo do nosso teste
        Client retrievedClient = testEntityManager.find(Client.class, client.getId());

        assertThat(retrievedClient).isNotNull();
        assertThat(retrievedClient.getName()).isEqualTo(CLIENT.getName());
        assertThat(retrievedClient.getEmail()).isEqualTo(CLIENT.getEmail());
        assertThat(retrievedClient.getDocument()).isEqualTo(CLIENT.getDocument());
        assertThat(retrievedClient.getPhoneNumber()).isEqualTo(CLIENT.getPhoneNumber());
    }

    @Test
    public void createClientWithInvalidDataThrowsException(){

        //simulando dados invalidos
        Client emptyClient = new Client();
        Client invalidDataClient = new Client("","","","");

        assertThatThrownBy(() -> clientRepository.save(emptyClient)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> clientRepository.save(invalidDataClient)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void createClientWithDuplicateNameThrowsException(){

        Client clientInDataSaved = testEntityManager.persistFlushFind(CLIENT);
        testEntityManager.detach(clientInDataSaved);
        clientInDataSaved.setId(null);

        assertThatThrownBy(() -> clientRepository.save(clientInDataSaved)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getClientByExistingId() {
        Client client = testEntityManager.persistFlushFind(CLIENT);
        Optional<Client> clientOpt = clientRepository.findById(client.getId());

        assertThat(clientOpt).isNotEmpty();
        assertThat(clientOpt.get()).isEqualTo(client);

    }

    @Test
    public void getClientByUnexistingIdEmpty() {
        Optional<Client> clientOpt = clientRepository.findById(1L);

        assertThat(clientOpt).isEmpty();
    }

    @Test
    public void getClientByExistingName() {
        Client client = testEntityManager.persistFlushFind(CLIENT);

        List<Client> clientsList = clientRepository.findByNameContainingIgnoreCase(client.getName());

        assertThat(clientsList).isNotEmpty();
        assertThat(clientsList.get(0)).isEqualTo(client);
    }

    @Test
    public void getClientByUnexistingNameNotFound() {
        List<Client> clientList = clientRepository.findByNameContainingIgnoreCase("name");

        assertThat(clientList).isEmpty();
    }

    @Test
    public void listClientsReturnsFilteredClients() {

        Client persistedClient1 = testEntityManager.persistFlushFind(CLIENT);
        //Client persistedClient2 = testEntityManager.persistFlushFind(CLIENT2);

        List<Client> clients = clientRepository.findByNameContainingIgnoreCase("pa");

        assertThat(clients).isNotEmpty();
        assertThat(clients).contains(persistedClient1); //, persistedClient2
    }

    @Test
    public void listClientsReturnsNoClients() {

        List<Client> clients = clientRepository.findByNameContainingIgnoreCase("nonexistent");

        assertThat(clients).isEmpty();
    }

    @Test
    public void deleteClientWithExistingIdDatabase() {
        Client client = testEntityManager.persistFlushFind(CLIENT);

        clientRepository.deleteById(client.getId());

        Client removedClient = testEntityManager.find(Client.class, client.getId());
        assertThat(removedClient).isNull();
    }

    @Test
    public void deleteClientWithNonExistentIdThrowException() {
        try {
            // Tente deletar um ID inexistente
            clientRepository.deleteById(999L);
        } catch (Exception e) {
            // Imprima a exceção para identificar o tipo
            System.out.println("Exceção lançada: " + e.getClass().getName());
            System.out.println("Mensagem da exceção: " + e.getMessage());
            // Re-lance a exceção para falhar o teste
            throw e;
        }
    }


}