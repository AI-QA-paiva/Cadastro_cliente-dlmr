package com.clientes.cadastro.dto;

import com.clientes.cadastro.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter; // TODO import n utilizado
import lombok.Setter; // TODO: Remova se não estiver usando Lombok para getters e setters.
import org.hibernate.validator.constraints.Length; // TODO import n utilizado

public class ClientRequestDTO {

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

    // TODO: Considere usar a anotação @AllArgsConstructor e @NoArgsConstructor do Lombok para reduzir o boilerplate de construtores.
    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String name, String email, String document, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    // TODO: Considere usar as anotações @Getter e @Setter do Lombok para evitar a necessidade de criar manualmente os métodos getters e setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // TODO: Adicione validação no setter para evitar valores nulos ou inválidos, caso necessário.
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // TODO: Adicione validação no setter para garantir que o email seja válido (ex.: regex para formato de email).
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        // TODO: Adicione validação no setter para garantir que o documento seja válido (ex.: CPF ou CNPJ).
        this.document = document;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // TODO: Adicione validação no setter para garantir que o número de telefone seja válido (ex.: regex para formato de telefone).
        this.phoneNumber = phoneNumber;
    }

    // TODO: O método `convertToEntity` viola o princípio de responsabilidade única (SRP) do SOLID. Considere movê-lo para uma classe de conversão ou um mapper (ex.: MapStruct).
    public Client convertToEntity(ClientRequestDTO clientRequestDTO) {
        return new Client(
                null, // TODO: Certifique-se de que o ID seja gerenciado corretamente pela entidade e não seja necessário aqui.
                name,
                email,
                document,
                phoneNumber
        );
    }
}
