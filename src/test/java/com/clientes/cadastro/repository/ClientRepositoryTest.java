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

//@SpringBootTest(classes = ClientRepository.class) //1- no parametro é a classe que queremos carregar nesse teste; caso contrário, vai carregar todas as beans (reduz performance)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired //2- a partir do momento que tenho a @SpringBootTet, consigo informar para o test a qual classe se refere essa injeção
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager testEntityManager; //para interagir com o BD e poder consultar os dados que estão la

    @AfterEach
    public void afterEach(){
        CLIENT.setId(null);
    }

    @Test
    public void createClient_WithValidData_ReturnClient(){

        Client client = clientRepository.save(CLIENT);

        //alvo do nosso teste
        Client sutSearchClient = testEntityManager.find(Client.class, client.getId());

        System.out.println(sutSearchClient);

        assertThat(sutSearchClient).isNotNull();
        assertThat(sutSearchClient.getName()).isEqualTo(CLIENT.getName());
        assertThat(sutSearchClient.getEmail()).isEqualTo(CLIENT.getEmail());
        assertThat(sutSearchClient.getDocument()).isEqualTo(CLIENT.getDocument());
        assertThat(sutSearchClient.getPhoneNumber()).isEqualTo(CLIENT.getPhoneNumber());
    }

    @Test
    public void createClient_WithInvalidData_ReturnThrowsException(){

        //simulando dados invalidos
        Client empytClient = new Client();
        Client invalidDataClient = new Client("","","","");

        assertThatThrownBy(() -> clientRepository.save(empytClient)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> clientRepository.save(invalidDataClient)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createClient_WithExistingName_ThrowsException(){

        Client clientInDataSaved = testEntityManager.persistFlushFind(CLIENT);
        testEntityManager.detach(clientInDataSaved); //para tirar a visibilidade deste objeto encontrado no BD (linha acima), para não interpretar como se fosse um update
        clientInDataSaved.setId(null); //coloca null para retirar o id e tentar salvar ele novamente, porém o nome nao poderá repetir pois é unico, e retornará erro

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

//    @Test
//    public void listClients_ReturnsFilteredClients() {
//
//    }

//    @Test
//    public void listClients_ReturnsNoClients() {
//
//    }

    @Test
    public void removeClient_WithExistingId_RemovesClientFromDatabase() {
        Client client = testEntityManager.persistFlushFind(CLIENT);

        clientRepository.deleteById(client.getId());

        Client removedClient = testEntityManager.find(Client.class, client.getId());
        assertThat(removedClient).isNull();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        assertThatThrownBy(() -> clientRepository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }





}
