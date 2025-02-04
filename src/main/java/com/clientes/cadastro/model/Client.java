package com.clientes.cadastro.model;

import jakarta.persistence.*; // TODO remover wildcard
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
//@Data // TODO se n usar remova e n deixe comentado, isso polui o codigo
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String document;

    @NotEmpty
    @Column(nullable = false)
    private String phoneNumber;

    // TODO: Considere usar a anotação @Builder (Lombok) para facilitar a criação de objetos Client, especialmente em testes ou cenários complexos.
    public Client() {
    }

    public Client(Long id, String name, String email, String document, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    // TODO: Avalie se o construtor sem o atributo `id` é realmente necessário. Caso seja, documente o motivo para evitar confusão.
    public Client(String name, String email, String document, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        // TODO: Considere remover o setter de `id` para garantir que ele seja gerenciado apenas pelo JPA, evitando inconsistências.
        this.id = id;
    }

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

    // TODO: Considere usar a anotação @EqualsAndHashCode (Lombok) para simplificar a implementação do método equals.
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    // TODO: Considere usar a anotação @ToString (Lombok) para simplificar a implementação do método toString.
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", document='" + document + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}