package com.clientes.cadastro.controller;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.regex.Matcher;

@WebMvcTest(ClientController.class) //aqui indicamos que não é para subir todos os controllers, somente esse indicado
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    public void createClient_WithValidDate_ReturnsCreated() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        // TODO: Renomear o método para "createClientWithValidDataReturnsCreated" (correção de typo em "Date" para "Data").
        when(clientService.registerClient(CLIENT)).thenReturn(CLIENT);

        mockMvc.perform(post("/client")
                        .content(objectMapper.writeValueAsString(CLIENT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(CLIENT)); // TODO: Validar campos específicos do CLIENT em vez de validar o objeto inteiro para maior precisão.
    }

    @Test
    public void createClient_WithInvalidData_ReturnsBadRequest() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        // TODO: Renomear o método para "createClientWithInvalidDataReturnsUnprocessableEntity" para refletir o status correto (422).
        Client emptyClient = new Client();
        Client invalidDataClient = new Client("", "", "", "");

        mockMvc.perform(post("/client")
                        .content(objectMapper.writeValueAsString(emptyClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/client")
                        .content(objectMapper.writeValueAsString(invalidDataClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createClient_WithExistingName_ReturnsConflict() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        // TODO: Adicionar validação para garantir que o CLIENT simulado tenha um nome único antes de lançar a exceção.
        when(clientService.registerClient(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/client")
                        .content(objectMapper.writeValueAsString(CLIENT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getClient_ByExistingId_ReturnsClient() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        when(clientService.getOnlyOneClient(1L)).thenReturn(Optional.of(CLIENT));

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(CLIENT)); // TODO: Validar campos específicos do CLIENT em vez de validar o objeto inteiro.
    }

    @Test
    public void getClient_ByUnexistingId_ReturnsNotFound() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        mockMvc.perform(get("/client/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getClient_ByExistingName_ReturnsClient() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        when(clientService.getByName(CLIENT.getName())).thenReturn(Optional.of(CLIENT));

        mockMvc.perform(get("/client/name/" + CLIENT.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(CLIENT.getName()))
                .andExpect(jsonPath("$.id").value(CLIENT.getId()));
    }

    @Test
    public void getClient_ByUnexistingName_ReturnsNotFound() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        mockMvc.perform(get("/client/name/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeClient_WithExistingId_ReturnsNoContent() throws Exception {//TODO em java n se usa snake case para nomes de metodos
        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeClient_WithUnexistingId_ReturnsNotFound() throws Exception { //TODO em java n se usa snake case para nomes de metodos
        final Long clientId = 1L;

        doThrow(new EmptyResultDataAccessException(1)).when(clientService).deleteCustomerRecord(clientId);

        mockMvc.perform(delete("/client/" + clientId)) // TODO: Corrigir a URL concatenada para "/client/" + clientId (adicionar barra antes do ID).
                .andExpect(status().isNotFound());
    }

    // TODO: Implementar os testes comentados para listar clientes, incluindo cenários com e sem resultados.
    // @Test
    // public void listClientsReturnsFilteredClients() throws Exception {
    // }

    // @Test
    // public void listClientsReturnsNoClients() throws Exception {
    // }
}

//usar a anotação do spring mvc >> @WebMvcTest >> usaremos para todos os testes na camada controller
//para testar esse retorno, preciso fazer uma requisião HHTP que sensibiliza a controller >> já envolve o contexto web
//uma vez inserido, alem de injetar a classe controller, também criará um contexto web proximo do real, simulando o papel do dublê Fake

//mockMvc simulador de requisições HTTP em testes de controladores; 'perform' faz um get no endpoint é mapeado
//como não ha MockBean que carregue um cliente >> dará com inexistente Not_found 404 >> o que casa com a expectativa andExpect