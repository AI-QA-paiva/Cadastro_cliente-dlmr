package com.clientes.cadastro.dto;

import com.clientes.cadastro.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class ClientRequestDTO {


    //@NotBlank(message = "Favor informar um nome")
    //@NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String name;

   // @NotBlank(message = "É necessário informar um endereço de e-mail")
    //@Email(message = "E-mail Inválido! Verifique se está correto e sem erros de formatação")
    private String email;

   // @NotBlank(message = "O Preenchimento deste campo é Obrigatório")
    private String document;

   // @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String phoneNumber;

    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String name, String email, String document, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // conversor

    public Client convertToEntity(ClientRequestDTO clientRequestDTO){
        return new Client(
                null,
                name,
                email,
                document,
                phoneNumber
        );
    }

}
