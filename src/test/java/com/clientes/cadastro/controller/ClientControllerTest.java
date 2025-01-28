package com.clientes.cadastro.controller;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void createClient_WithValidDate_ReturnsCreated() throws Exception {

        when(clientService.registerClient(CLIENT)).thenReturn(CLIENT);

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(CLIENT)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(CLIENT));//onde pega o conteudo trafegado com o string e transformar em objeto >> assim vou conseguir validar com o CLIENT
    }

    @Test
    public void creatClient_WithInvalidData_ReturnsBadRequest() throws Exception {

        //simulando dados invalidos
        Client empytClient = new Client();
        Client invalidDataClient = new Client("","","","");

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(empytClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(invalidDataClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createClient_WithExistingName_ReturnsConflict() throws Exception {

        //quando tentar um clinet que ja existe >> lança uma exceção e cai no tratamento no controller
        //inserir um dubê stub
        when(clientService.registerClient(any())).thenThrow(DataIntegrityViolationException.class); //any para indicar que qualquer input passado, não importa o que, sempre lançará uma exceção

        mockMvc.perform(post("/client").content(objectMapper.writeValueAsString(CLIENT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void getClent_ByExistingId_ReturnsClient() throws Exception {
        when(clientService.getOnlyOneClient(1L)).thenReturn(Optional.of(CLIENT));

        mockMvc
                .perform(
                        get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(CLIENT));
    }

    @Test
    public void getClient_ByUnexistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/client/1"))
                .andExpect(status().isNotFound());

    }

//    @Test
//    public void getClient_ByExistingName_ReturnsClient() throws Exception {
//        when(clientService.getByName(CLIENT.getName())).thenReturn(Optional.of(CLIENT));
//
//        mockMvc
//                .perform(
//                        get("/planets/name/" + PLANET.getName()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(PLANET));
//    }
//
//    @Test
//    public void getPlanet_ByUnexistingName_ReturnsNotFound() throws Exception {
//        mockMvc.perform(get("/planets/name/1"))
//                .andExpect(status().isNotFound());
//    }


}

//usar a anotação do spring mvc >> @WebMvcTest >> usaremos para todos os testes na camada controller
//para testar esse retorno, preciso fazer uma requisião HHTP que sensibiliza a controller >> já envolve o contexto web
//uma vez inserido, alem de injetar a classe controller, também criará um contexto web proximo do real, simulando o papel do dublê Fake