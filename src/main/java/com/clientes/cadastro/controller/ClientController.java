package com.clientes.cadastro.controller;

import com.clientes.cadastro.model.Client;
import com.clientes.cadastro.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(path="/client")
    public ResponseEntity<Client> creatClient (@Valid @RequestBody Client client){

        Client newClient = clientService.registerClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @GetMapping(path = "/clients")
    public ResponseEntity<List<Client>> listClients (){
        return ResponseEntity.ok(clientService.searchForClients());
    }

    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Optional<Client>> customerInformationId (@PathVariable Long id){
        return ResponseEntity.ok(clientService.getOnlyOneClient(id));
    }

    @PutMapping(path = "/client/{id}")
    public ResponseEntity<Client> customerRegistrationAdjustment(@RequestBody Client client, @PathVariable Long id){
        client.setId(id);
        return ResponseEntity.ok(clientService.registeredClient(client));
    }

    @DeleteMapping(path = "/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteCustomerRecord(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }



}
