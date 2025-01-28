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


    @Operation(description = "Retornará uma lista de clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Retornará lista encontrada")
    @GetMapping(path = "/clients")
    public ResponseEntity<List<Client>> listClients (){
        return ResponseEntity.ok(clientService.searchForClients());
    }

    @Operation(description = "Retornará os dados do cliente, conforme o seu Id cadastrado na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados do cliente conforme estiver cadastrado na base de dados")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Optional<Client>> locateClientId (@PathVariable Long id){
        Optional<Client> client = clientService.getOnlyOneClient(id);
        if(client.isPresent()) {
            return ResponseEntity.ok(client);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Retornará os dados do cliente pesquisado pelo nome")
    @ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    @GetMapping("/client/name/{name}")
    public ResponseEntity<Client> getByName(@PathVariable("name") String name){
        return clientService.getByName(name)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
    @ApiResponse(responseCode = "204", description = "Retornará operação de exclusão executada")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @DeleteMapping(path = "/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteCustomerRecord(id);
        return ResponseEntity.noContent().build();
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}


/* ESTUDAR O CASO: >> com pesquisar por nome que pode me trzer uma lista porque tem várias pessoas com um nome em comum???*/
//@Operation(description = "Retornará os dados do cliente consultado pelo nome")
//@ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)")
//@ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
//@GetMapping("/client/name/{name}")
//public ResponseEntity<List<Client>> getByName(@PathVariable("name") String name){
//    List<Client> clients = clientService.getByName(name);
//    if(!clients.isEmpty()){
//        return ResponseEntity.ok(clients);
//    }else {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//}