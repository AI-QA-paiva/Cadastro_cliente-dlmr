package com.clientes.cadastro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static com.clientes.cadastro.common.ClientConstants.CLIENT;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    public void createClient_ReturnClient(){

        //principio dos testes conhecido como AAA
        //Arrange
        when(clientRepository.save(CLIENT)).thenReturn(CLIENT);
        //ação
        Client sutClient = clientService.registerClient(CLIENT);
        //resultado
        assertThat(sutClient).isEqualTo(CLIENT);

    }

}
