package com.clientes.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static com.clientes.cadastro.common.ClientConstants.INVALID_CLIENT;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = ClientService.class) //uso quando faço teste via springboot vai mapear Beans do projeto; classes eu passo apenas a bean que preciso subir
public class ClientServiceTeste {

    //@Autowired
    @InjectMocks //instancia realmente um ClientService + cria mocks automaticamente para as dependencias que essa classe tenha
    private ClientService clientService; //injetando uma dependência como objeto, referenciando a classe service//para que eu possa ter acesso aos métodos que testarei

    //@MockBean
    @Mock
    private ClientRepository clientRepository;

    @Test
    public void createClient_WithValidData_ReturnClient(){

        //principio do teste conhecido como AAA
        when(clientRepository.save(CLIENT)).thenReturn(CLIENT); //Arrange

        Client sutClient = clientService.registerClient(CLIENT); //ação

        assertThat(sutClient).isEqualTo(CLIENT); //resultado
    }

    @Test
    public void createClient_WithInvalidData_ReturnThrowsException(){
        when(clientRepository.save(INVALID_CLIENT)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> clientService.registerClient(INVALID_CLIENT)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getClient_ByExistingId_ReturnsClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(CLIENT));

        Optional<Client> sutClient = clientService.getOnlyOneClient(1L);

        assertThat(sutClient).isNotEmpty();
        assertThat(sutClient.get()).isEqualTo(CLIENT);
    }

    @Test
    public void geClient_ByUnexistingId_ReturnsEmpty() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Client> sut = clientService.getOnlyOneClient(1L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getClient_ByExistingName_ReturnsClient() {
        when(clientRepository.findByName(CLIENT.getName())).thenReturn(Optional.of(CLIENT));

        Optional<Client> sut = clientService.getByName(CLIENT.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CLIENT);
    }

    @Test
    public void getClient_ByUnexistingName_ReturnsEmpty() {
        final String name = "Unexisting name";
        when(clientRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<Client> sut = clientService.getByName(name);

        assertThat(sut).isEmpty();
    }

    @Test
    public void listClients_ReturnsAllClients() {
        List<Client> clients = new ArrayList<>() {
            {
                add(CLIENT);
            }
        };
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> sut = clientService.searchForClients();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(CLIENT);
    }

    @Test
    public void listClients_ReturnsNoClients() {

        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        List<Client> sut = clientService.searchForClients();

        assertThat(sut).isEmpty();
    }

    @Test
    public void removeClient_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> clientService.deleteCustomerRecord(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removeClient_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(clientRepository).deleteById(99L);

        assertThatThrownBy(() -> clientService.deleteCustomerRecord(99L)).isInstanceOf(RuntimeException.class);
    }


}
