package com.clientes.cadastro.controller;

import com.clientes.cadastro.dto.ClientRequestDTO;
import com.clientes.cadastro.dto.ClientResponseDTO;
import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.clientes.cadastro.dto.ClientResponseDTO.convertToResponseDTO;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    //private final ClientResponseDTO responseDTO = new ClientResponseDTO();


    @Operation(description = "Criará um novo cadastro para um novo cliente na base de dados")
    @ApiResponse(responseCode = "201", description = "Retornará que o Cliente foi cadastrado e o id respectivo na na base de dados")
    @PostMapping(path="/client")
    public ResponseEntity<ClientResponseDTO> creatClient (@Valid @RequestBody ClientRequestDTO clientRequestDTO){

        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        Client newClient = clientService.registerClient(client);

        //ClientResponseDTO.convertToResponseDTO(newClient);
        //responseDTO.convertToResponseDTO(newClient);
        return new ResponseEntity<>(ClientResponseDTO.convertToResponseDTO(newClient), HttpStatus.CREATED);
    }


    @Operation(description = "Retornará uma lista de clientes que estão cadastrados na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará que a lista foi encontrada com sucesso na base de dados")
    @GetMapping(path = "/clients")
    public ResponseEntity<List<Client>> listClients (){
        return ResponseEntity.ok(clientService.searchForClients());
    }


    @Operation(description = "Retornará os dados do cliente, conforme o seu Id cadastrado na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados do cliente conforme estiver cadastrado na base de dados")
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Optional<Client>> locateClientId (@PathVariable Long id){
        return ResponseEntity.ok(clientService.getOnlyOneClient(id));
    }


    @Operation(description = "Alterará algum dado do cliente ajustando/atualizando sua informação na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados ajustados/alterados do cliente com base no seu id cadastrado na base de dados")
    @PutMapping(path = "/client/{id}")
    public ResponseEntity<ClientResponseDTO> changeCustomerDataId(@RequestBody ClientRequestDTO clientRequestDTO, @PathVariable Long id){

        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        client.setId(id);
        Client clientChanged = clientService.registeredClient(client);
        ClientResponseDTO updateClientDTO = convertToResponseDTO(clientChanged);
        return ResponseEntity.ok(updateClientDTO);
    }


    @Operation(description = "Excluirá o cliente com base no Id, retirando seus dados da base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará que a operação de exclusão do cliente na base de dados foi executada")
    @DeleteMapping(path = "/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteCustomerRecord(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}
