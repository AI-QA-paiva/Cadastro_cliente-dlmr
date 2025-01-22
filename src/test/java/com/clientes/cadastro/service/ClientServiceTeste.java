package com.clientes.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = ClientService.class) //uso quando faço teste via springboot
public class ClientServiceTeste {

    @Autowired
    private ClientService clientService; //injetando uma dependência como objeto, referenciando a classe service//para que eu possa ter acesso aos métodos que testarei

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void createClient(){

        //preparação do teste
        Client client = new Client();
        client.setName("joao");
        client.setEmail("a123@hotmail.com.br");
        client.setDocument("123456");
        client.setPhoneNumber("85858585858");

        when(clientRepository.save(client)).thenReturn(client);
        //ação
        Client sutClient = clientService.registerClient(client);
        //resultado
        assertThat(sutClient).isEqualTo(client);

    }



}
