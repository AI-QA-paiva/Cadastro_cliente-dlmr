package com.clientes.cadastro.controller;

import com.clientes.cadastro.dto.ClientRequestDTO;
import com.clientes.cadastro.dto.ClientResponseDTO;
import com.clientes.cadastro.exception.BadRequest;
import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clientes.cadastro.dto.ClientResponseDTO.convertToResponseDTO;

@RestController
public class ClientController {

    private ClientService clientService;
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @Operation(description = "Criará um novo cadastro para um novo cliente na base de dados")
    @ApiResponse(responseCode = "201", description = "Retornará que o Cliente foi cadastrado e o id respectivo na na base de dados")
    @PostMapping(path="/client")
    public ResponseEntity<ClientResponseDTO> createClient (@Valid @RequestBody ClientRequestDTO clientRequestDTO) throws Exception{

        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        try{
            Client newClient = clientService.registerClient(client);
            return new ResponseEntity<>(ClientResponseDTO.convertToResponseDTO(newClient), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Retorna 409 em caso de conflito
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(description = "Retornará uma lista de clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Retornará lista encontrada")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    @GetMapping(path = "/clients")
    public ResponseEntity<List<ClientResponseDTO>> listClients (){

        List<Client> searchClient = clientService.searchForClients();

        List<ClientResponseDTO> clientsDto = new ArrayList<>();

        for(Client client : searchClient){
            ClientResponseDTO foundClientes = ClientResponseDTO.convertToResponseDTO(client);
            clientsDto.add(foundClientes);
        }
        return ResponseEntity.ok(clientsDto);
    }

    @Operation(description = "Retornará os dados do cliente, conforme o seu Id cadastrado na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados do cliente conforme estiver cadastrado na base de dados")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<ClientResponseDTO> locateClientId (@PathVariable Long id) throws Exception{
        Optional<Client> client = clientService.getOnlyOneClient(id);
        if(client.isPresent()) {
            ClientResponseDTO clientResponseDTO = ClientResponseDTO.convertToResponseDTO(client.get());
            return ResponseEntity.ok(clientResponseDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Retornará os dados do cliente pesquisado pelo nome")
    @ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    @GetMapping("/client/listsearch/{namePart}")
    public ResponseEntity<List<ClientResponseDTO>> getByNameOrParts(@PathVariable("namePart") String namePart) {

        List<Client> searchClients = clientService.searchByNameParts(namePart);

        List<ClientResponseDTO> clientResponseDTO = new ArrayList<>();

        if (!searchClients.isEmpty()) {

            //List<ClientResponseDTO> clientResponseDTO = new ArrayList<>();

            for (Client client : searchClients) {
                ClientResponseDTO foundClient = ClientResponseDTO.convertToResponseDTO(client);
                clientResponseDTO.add(foundClient);
            }
            return ResponseEntity.ok(clientResponseDTO);
        }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

    }

    @Operation(description = "Alterará algum dado do cliente ajustando/atualizando sua informação na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados ajustados/alterados do cliente com base no seu id cadastrado na base de dados")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    @PutMapping(path = "/client/{id}")
    public ResponseEntity<ClientResponseDTO> updateDataClientId(@RequestBody ClientRequestDTO clientRequestDTO, @PathVariable Long id) throws Exception{

        //checar se o id existe >> senão deve dar uma BadRequest

        Optional<Client> findClient = clientService.getOnlyOneClient(id);
        if(!findClient.isPresent()){
            throw new BadRequest("Client not found with id: "+ id);
        }

        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        client.setId(id);
        Client clientChanged = clientService.updateClientId(client);
        ClientResponseDTO updateClientDTO = convertToResponseDTO(clientChanged);
        return ResponseEntity.ok(updateClientDTO);
    }

    @Operation(description = "Excluirá o cliente com base no Id, retirando seus dados da base de dados")
    @ApiResponse(responseCode = "204", description = "Retornará operação de exclusão executada")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @DeleteMapping(path = "/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) throws Exception{
        clientService.deleteCustomerRecord(id);
        return ResponseEntity.noContent().build();
    }
}