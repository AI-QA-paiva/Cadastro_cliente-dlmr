package com.clientes.cadastro.dto;

import com.clientes.cadastro.model.Client;
import lombok.Getter;

// TODO: Considere usar a anotação @Getter do Lombok para evitar a necessidade de criar manualmente os métodos getters.
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String document;
    private String phoneNumber;

    // TODO: Considere usar a anotação @NoArgsConstructor do Lombok para reduzir o boilerplate do construtor vazio.
    public ClientResponseDTO() {
    }

    // TODO: Considere usar a anotação @AllArgsConstructor do Lombok para reduzir o boilerplate do construtor completo.
    public ClientResponseDTO(Long id, String name, String email, String document, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    // TODO: Se o Lombok for usado, remova os getters manuais e substitua pela anotação @Getter.
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

    // TODO: O método `convertToResponseDTO` viola o princípio de responsabilidade única (SRP) do SOLID.
    // Considere movê-lo para uma classe de conversão ou um mapper (ex.: MapStruct) para seguir o princípio SRP.
    public static ClientResponseDTO convertToResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getDocument(),
                client.getPhoneNumber()
        );
    }
}

//        Resumo dos ajustes sugeridos:
//        Uso de Lombok:
//
//        Considere usar as anotações @Getter, @NoArgsConstructor e @AllArgsConstructor para reduzir o boilerplate e melhorar a legibilidade do código.
//        Se o Lombok for usado, remova os métodos manuais de getters e construtores.
//        Princípio de Responsabilidade Única (SRP):
//
//        O método convertToResponseDTO não deveria estar na classe DTO. Ele pode ser movido para uma classe de conversão ou um mapper (ex.: MapStruct) para seguir o princípio SRP.
//        Documentação:
//
//        Adicione comentários explicando decisões de design, como o uso de métodos ou validações específicas.
//        Clean Code:
//
//        Remova dependências ou anotações desnecessárias, como @Getter, se não estiverem sendo usadas.
//        Certifique-se de que o código seja consistente com o restante do projeto.

//TODO Considere usar uma biblioteca como MapStruct para gerenciar a conversão entre entidades e DTOs, o que tornará o código mais limpo e fácil de manter. Por exemplo:
//
//
//
//@Mapper
//public interface ClientMapper {
//    ClientResponseDTO toResponseDTO(Client client);
//}