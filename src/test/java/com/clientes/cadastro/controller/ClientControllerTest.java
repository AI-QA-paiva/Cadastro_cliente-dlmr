package com.clientes.cadastro.controller;

import static com.clientes.cadastro.common.ClientConstants.CLIENT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.clientes.cadastro.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
                .andExpect(jsonPath("$").value(CLIENT)) ;//onde pega o conteudo trafegado com o string e transformar em objeto >> assim vou conseguir validar com o CLIENT
    }

}

//usar a anotação do spring mvc >> @WebMvcTest >> usaremos para todos os testes na camada controller
//para testar esse retorno, preciso fazer uma requisião HHTP que sensibiliza a controller >> já envolve o contexto web
//uma vez inserido, alem de injetar a classe controller, também criará um contexto web proximo do real, simulando o papel do dublê Fake