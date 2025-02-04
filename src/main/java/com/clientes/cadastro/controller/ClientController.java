package com.clientes.cadastro.controller;

import com.clientes.cadastro.dto.ClientRequestDTO;
import com.clientes.cadastro.dto.ClientResponseDTO;
import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses; // TODO import n utilizado
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
    // TODO: Substituir @Autowired por injeção de dependência via construtor para maior testabilidade e aderência ao princípio de inversão de dependência (SOLID).
    // private final ClientService clientService;
    // public ClientController(ClientService clientService) {
    //     this.clientService = clientService;
    // }

    @Operation(description = "Criará um novo cadastro para um novo cliente na base de dados")
    @ApiResponse(responseCode = "201", description = "Retornará que o Cliente foi cadastrado e o id respectivo na base de dados")
    @PostMapping(path = "/client")
    public ResponseEntity<ClientResponseDTO> creatClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        // TODO: Renomear o método para "createClient" (correção de typo e maior clareza).
        // TODO: Validar se o cliente já existe antes de criar um novo, para evitar duplicidade de dados.
        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        Client newClient = clientService.registerClient(client);
        return new ResponseEntity<>(ClientResponseDTO.convertToResponseDTO(newClient), HttpStatus.CREATED);
    }

    @Operation(description = "Retornará uma lista de clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Retornará lista encontrada")
    @GetMapping(path = "/clients")
    public ResponseEntity<List<Client>> listClients() {
        // TODO: Considerar retornar uma lista de ClientResponseDTO em vez de entidades diretamente, para evitar expor o modelo de domínio.
        return ResponseEntity.ok(clientService.searchForClients());
    }

    @Operation(description = "Retornará os dados do cliente, conforme o seu Id cadastrado na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados do cliente conforme estiver cadastrado na base de dados")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Optional<Client>> locateClientId(@PathVariable Long id) {
        // TODO: Evitar retornar Optional no ResponseEntity. Retornar diretamente o objeto ou um erro 404.
        Optional<Client> client = clientService.getOnlyOneClient(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Retornará os dados do cliente pesquisado pelo nome")
    @ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
    @GetMapping("/client/name/{name}")
    public ResponseEntity<Client> getByName(@PathVariable("name") String name) {
        // TODO: Alterar o retorno para uma lista de clientes, pois podem existir múltiplos clientes com o mesmo nome.
        return clientService.getByName(name)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(description = "Alterará algum dado do cliente ajustando/atualizando sua informação na base de dados")
    @ApiResponse(responseCode = "200", description = "Retornará os dados ajustados/alterados do cliente com base no seu id cadastrado na base de dados")
    @PutMapping(path = "/client/{id}")
    public ResponseEntity<ClientResponseDTO> changeCustomerDataId(@RequestBody ClientRequestDTO clientRequestDTO, @PathVariable Long id) {
        // TODO: Validar se o cliente existe antes de tentar atualizá-lo. Retornar 404 caso não exista.
        Client client = clientRequestDTO.convertToEntity(clientRequestDTO);
        client.setId(id);
        // TODO: Renomear o método "registeredClient" para algo mais claro, como "updateClient".
        Client clientChanged = clientService.registeredClient(client);
        ClientResponseDTO updateClientDTO = convertToResponseDTO(clientChanged);
        return ResponseEntity.ok(updateClientDTO);
    }

    @Operation(description = "Excluirá o cliente com base no Id, retirando seus dados da base de dados")
    @ApiResponse(responseCode = "204", description = "Retornará operação de exclusão executada")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @DeleteMapping(path = "/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        // TODO: Validar se o cliente existe antes de tentar excluí-lo. Retornar 404 caso não exista.
        clientService.deleteCustomerRecord(id);
        return ResponseEntity.noContent().build();
    }
}

/* ESTUDAR O CASO: >> com pesquisar por nome que pode me trazer uma lista porque tem várias pessoas com um nome em comum??? */
// TODO: Implementar o endpoint comentado para retornar uma lista de clientes ao pesquisar por nome.
// @Operation(description = "Retornará os dados do cliente consultado pelo nome")
// @ApiResponse(responseCode = "200", description = "Cliente(s) encontrado(s)")
// @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado")
// @GetMapping("/client/name/{name}")
// public ResponseEntity<List<Client>> getByName(@PathVariable("name") String name) {
//     List<Client> clients = clientService.getByName(name);
//     if (!clients.isEmpty()) {
//         return ResponseEntity.ok(clients);
//     } else {
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//     }
// }