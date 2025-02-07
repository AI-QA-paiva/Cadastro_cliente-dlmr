package com.clientes.cadastro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDTO {

    @NotBlank(message = "Favor informar um nome")
    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String name;

    @NotBlank(message = "É necessário informar um endereço de e-mail")
    @Email(message = "E-mail Inválido! Verifique a formatação ou se digitou corretamente")
    private String email;

    @NotBlank(message = "O Preenchimento deste campo é Obrigatório")
    private String document;

    @NotEmpty(message = "O Preenchimento deste campo é Obrigatório")
    private String phoneNumber;

}
