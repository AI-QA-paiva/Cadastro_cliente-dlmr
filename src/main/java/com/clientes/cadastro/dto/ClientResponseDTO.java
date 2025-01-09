package com.clientes.cadastro.dto;

import com.clientes.cadastro.model.Client;
import lombok.Getter;

//@Getter
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String document;
    private String phoneNumber;

    public ClientResponseDTO() {
    }

    public ClientResponseDTO(Long id, String name, String email, String document, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDocument() {
        return document;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static ClientResponseDTO convertToResponseDTO(Client client){
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getDocument(),
                client.getPhoneNumber()
        );
    }

}
