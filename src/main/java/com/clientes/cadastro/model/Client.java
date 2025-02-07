package com.clientes.cadastro.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Length(min = 5, message = "name has fewer characters than allowed")
    @Length(max = 80, message = "name has more characters than allowed")
    @EqualsAndHashCode.Include
    private String name;

    @NotEmpty(message = "this field cannot be null or empty")
    @Column(unique=true, nullable = false)
    @Email(message = "email in invalid format")
    @EqualsAndHashCode.Include
    private String email;

    @NotEmpty(message = "this field cannot be null or empty")
    @Column(unique=true, nullable = false)
    @CPF(message = "número do registro de contribuinte individual brasileiro (CPF) inválido")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @EqualsAndHashCode.Include
    private String document;

    @NotEmpty(message = "this field cannot be null or empty")
    @Column(nullable = false)
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (00) 0000-0000 ou (00) 00000-0000")
    @EqualsAndHashCode.Include
    private String phoneNumber;

    //para usar nos testes na classe  ClientConstants pasta common >> nao terei Id gerado então necessitar pegar sem o atributo id
    public Client(String name, String email, String document, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.phoneNumber = phoneNumber;
    }

}
