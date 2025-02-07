package com.clientes.cadastro.controller;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static com.clientes.cadastro.common.ClientConstants.CLIENT_REQUEST_DTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.clientes.cadastro.common.ClientConstants;
import com.clientes.cadastro.dto.ClientRequestDTO;
import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    public void createClientWithValidDate() throws Exception {

        ClientRequestDTO clientRequestDTO = ClientConstants.CLIENT_REQUEST_DTO;
        when(clientService.registerClient(any(Client.class))).thenReturn(CLIENT);

        mockMvc.perform(post("/client")
                        .content(objectMapper.writeValueAsString(clientRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(CLIENT.getName()))
                .andExpect(jsonPath("$.email").value(CLIENT.getEmail()))
                .andExpect(jsonPath("$.documentSsr").value(CLIENT.getDocument())) // Alterado de $.cpf para $.documentSsr
                .andExpect(jsonPath("$.phoneNumber").value(CLIENT.getPhoneNumber()));
    }

    @Test
    public void createClientWithInvalidDataBadRequest() throws Exception {

        //simulando dados invalidos
        Client emptyClient = new Client();
        Client invalidDataClient = new Client("","","","");

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(emptyClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(invalidDataClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createClientWithExistingNameReturnsConflict() throws Exception {

        when(clientService.registerClient(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(CLIENT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void getClentByExistingId() throws Exception {
        when(clientService.getOnlyOneClient(1L)).thenReturn(Optional.of(CLIENT));

        mockMvc
                .perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CLIENT.getId())) // Verifica o ID
                .andExpect(jsonPath("$.name").value(CLIENT.getName())) // Verifica o nome
                .andExpect(jsonPath("$.email").value(CLIENT.getEmail())) // Verifica o email
                .andExpect(jsonPath("$.documentSsr").value(CLIENT.getDocument())) // Verifica o documento
                .andExpect(jsonPath("$.phoneNumber").value(CLIENT.getPhoneNumber()));
    }

    @Test
    public void getClientByUnexistingIdNotFound() throws Exception {
        mockMvc.perform(get("/client/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getClientByExistingName() throws Exception {

        //List<Client> clientsList = List.of(CLIENT)
        when(clientService.searchByNameParts(CLIENT.getName())).thenReturn(List.of(CLIENT));

        mockMvc
                .perform(
                        get("/client/listsearch/" + CLIENT.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(CLIENT.getName()))
                .andExpect(jsonPath("$[0].id").value(CLIENT.getId()));
    }

    @Test
    public void getClientByUnexistingNameNotFound() throws Exception {

        mockMvc.perform(get("/client/search/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listClientsReturnsFilteredClients() throws Exception {


        List<Client> clients = List.of(CLIENT);

        when(clientService.searchForClients()).thenReturn(clients);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(clients.size()))
                .andExpect(jsonPath("$[0].name").value(CLIENT.getName()))
                .andExpect(jsonPath("$[0].email").value(CLIENT.getEmail()));

    }

    @Test
    public void listClientsReturnsNoClients() throws Exception {

        when(clientService.searchForClients()).thenReturn(List.of());

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    public void removeClientWithExistingIdReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeClientWithUnexistingIdReturnsNotFound() throws Exception {
        final Long clientId = 1L;

        doThrow(new EmptyResultDataAccessException(1)).when(clientService).deleteCustomerRecord(clientId);

        mockMvc.perform(delete("/client" + clientId))
                .andExpect(status().isNotFound());
    }

}
