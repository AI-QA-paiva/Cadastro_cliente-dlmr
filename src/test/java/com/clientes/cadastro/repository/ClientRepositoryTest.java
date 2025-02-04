package com.clientes.cadastro.repository;

import com.clientes.cadastro.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest // usa um banco em memória H2 >> com essa anotação não é necessário mais o @SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager testEntityManager; // para interagir com o BD e consultar os dados diretamente

    @AfterEach
    public void afterEach() {
        CLIENT.setId(null);
    }

    @Test
    public void createClient_WithValidData_ReturnClient() {
        Client client = clientRepository.save(CLIENT);

        // TODO: Evitar o uso de System.out.println, utilize um logger apropriado para depuração.
        System.out.println(client);

        // TODO: Renomear sutSearchClient para algo mais significativo, como retrievedClient.
        Client sutSearchClient = testEntityManager.find(Client.class, client.getId());

        assertThat(sutSearchClient).isNotNull();
        assertThat(sutSearchClient.getName()).isEqualTo(CLIENT.getName());
        assertThat(sutSearchClient.getEmail()).isEqualTo(CLIENT.getEmail());
        assertThat(sutSearchClient.getDocument()).isEqualTo(CLIENT.getDocument());
        assertThat(sutSearchClient.getPhoneNumber()).isEqualTo(CLIENT.getPhoneNumber());
    }

    @Test
    public void createClient_WithInvalidData_ReturnThrowsException() {
        // TODO: Renomear empytClient para emptyClient (correção de typo).
        Client empytClient = new Client();
        Client invalidDataClient = new Client("", "", "", "");

        // TODO: Validar se RuntimeException é a exceção correta a ser esperada. Considere criar exceções customizadas para maior clareza.
        assertThatThrownBy(() -> clientRepository.save(empytClient)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> clientRepository.save(invalidDataClient)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createClient_WithExistingName_ThrowsException() {
        Client clientInDataSaved = testEntityManager.persistFlushFind(CLIENT);
        testEntityManager.detach(clientInDataSaved);

        // TODO: Evitar manipulação direta de IDs. Considere criar um método utilitário para clonar objetos sem ID.
        clientInDataSaved.setId(null);

        // TODO: Validar se RuntimeException é a exceção correta a ser esperada.
        assertThatThrownBy(() -> clientRepository.save(clientInDataSaved)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getClient_ByExistingId_ReturnsClient() {
        Client client = testEntityManager.persistFlushFind(CLIENT);
        Optional<Client> clientOpt = clientRepository.findById(client.getId());

        assertThat(clientOpt).isNotEmpty();
        assertThat(clientOpt.get()).isEqualTo(client);
    }

    @Test
    public void getClient_ByUnexistingId_ReturnsEmpty() {
        Optional<Client> clientOpt = clientRepository.findById(1L);

        assertThat(clientOpt).isEmpty();
    }

    @Test
    public void getClient_ByExistingName_ReturnsClient() {
        Client client = testEntityManager.persistFlushFind(CLIENT);

        Optional<Client> clientOpt = clientRepository.findByName(client.getName());

        assertThat(clientOpt).isNotEmpty();
        assertThat(clientOpt.get()).isEqualTo(client);
    }

    @Test
    public void getClient_ByUnexistingName_ReturnsNotFound() {
        Optional<Client> clientOpt = clientRepository.findByName("name");

        assertThat(clientOpt).isEmpty();
    }

    @Test
    public void removeClient_WithExistingId_RemovesClientFromDatabase() {
        Client client = testEntityManager.persistFlushFind(CLIENT);

        clientRepository.deleteById(client.getId());

        // TODO: Renomear removedClient para algo mais significativo, como deletedClient.
        Client removedClient = testEntityManager.find(Client.class, client.getId());
        assertThat(removedClient).isNull();
    }

    @Test
    public void removeClient_WithUnexistingId_ThrowsException() {
        Optional<Client> clientOpt = clientRepository.findById(1L);
        assertThat(clientOpt).isEmpty();

        assertThatThrownBy(() -> clientRepository.deleteById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    // TODO: Implementar os testes listClients_ReturnsFilteredClients e listClients_ReturnsNoClients.
    // Considere criar métodos utilitários para configurar os dados de teste e evitar duplicação de código.
}