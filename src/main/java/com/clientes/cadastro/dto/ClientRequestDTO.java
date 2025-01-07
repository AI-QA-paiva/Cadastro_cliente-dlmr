package com.clientes.cadastro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ClientRequestDTO {


    @NotBlank
    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String name;

    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    @Email(message = "E-mail Inválido! Verifique se está correto e sem erros de formatação")
    private String email;

    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String document;

    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String phoneNumber;

    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String name, String email, String document, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

}
