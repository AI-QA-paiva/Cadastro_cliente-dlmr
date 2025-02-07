package com.clientes.cadastro.dto;

import com.clientes.cadastro.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String documentSsr;
    private String phoneNumber;


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
