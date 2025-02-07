package com.clientes.cadastro.service;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static com.clientes.cadastro.common.ClientConstants.INVALID_CLIENT1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


import com.clientes.cadastro.exception.BadRequest;
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

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {


    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void createClientWithValidData() throws Exception{

        when(clientRepository.save(CLIENT)).thenReturn(CLIENT); //Arrange

        Client sutSaveClient = clientService.registerClient(CLIENT); //ação

        assertThat(sutSaveClient).isEqualTo(CLIENT); //resultado
    }

    @Test
    public void createClientWithInvalidDataThrowsException(){
        when(clientRepository.save(INVALID_CLIENT1)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> clientService.registerClient(INVALID_CLIENT1)).isInstanceOf(RuntimeException.class);

        verify(clientRepository).save(INVALID_CLIENT1);
    }

    @Test
    public void getClientByExistingId() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(CLIENT));

        Optional<Client> sutClient = clientService.getOnlyOneClient(1L);

        assertThat(sutClient).isNotEmpty();
        assertThat(sutClient.get()).isEqualTo(CLIENT);
    }

    @Test
    public void getClientByUnexistingIdEmpty() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.getOnlyOneClient(1L))
                .isInstanceOf(BadRequest.class)
                .hasMessage("Cliente com o Id 1 não foi encontrado.");

        verify(clientRepository).findById(1L);
    }

    @Test
    public void getClientByExistingName() {

        //List<Client> clientsList = List.of(CLIENT);
        when(clientRepository.findByNameContainingIgnoreCase(CLIENT.getName())).thenReturn(List.of(CLIENT));

        List<Client> sutRetrievedClient = clientService.searchByNameParts(CLIENT.getName());

        assertThat(sutRetrievedClient).isNotEmpty();
        assertThat(sutRetrievedClient).hasSize(1);
        assertThat(sutRetrievedClient.get(0)).isEqualTo(CLIENT);
    }

    @Test
    public void getClientByUnexistingNameEmpty() {
        final String name = "Unexisting name";
        when(clientRepository.findByNameContainingIgnoreCase(name)).thenReturn(Collections.emptyList());

        List<Client> sutRetrievedClient = clientService.searchByNameParts(name);

        assertThat(sutRetrievedClient).isEmpty();
    }

    @Test
    public void listClientsAllClients() {
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
    public void listClientsReturnsNoClients() {

        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        List<Client> sut = clientService.searchForClients();

        assertThat(sut).isEmpty();
    }

    @Test
    public void removeClientWithExistingIdThrowAnyException() {

        Long existingId = 1L;
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(CLIENT));

        assertThatCode(() -> clientService.deleteCustomerRecord(existingId)).doesNotThrowAnyException();

        verify(clientRepository).deleteById(existingId);

    }

    @Test
    public void removeClientWithUnexistingIdThrowsException() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada ao tentar deletar um cliente inexistente
        assertThrows(BadRequest.class, () -> clientService.deleteCustomerRecord(99L));

        // Verifica que deleteById nunca foi chamado
        verify(clientRepository, never()).deleteById(99L);
    }


}
