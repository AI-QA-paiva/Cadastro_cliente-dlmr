package com.clientes.cadastro.common;

import com.clientes.cadastro.dto.ClientRequestDTO;
import com.clientes.cadastro.dto.ClientResponseDTO;
import com.clientes.cadastro.model.Client;

public class ClientConstants {

    public static final Client CLIENT = new Client("Pedro Paiva", "sideral1@email.com","381.118.240-41","(85) 9000-0001");
    public static final Client CLIENT2 = new Client("Jorge Amado", "sideral12@email.com","908.344.350-71","(85) 59000-0001");

    public static final Client INVALID_CLIENT = new Client("", "l","","");
    public static final Client INVALID_CLIENT1 = new Client("", "auto_01.com.br","","");

    public static final ClientRequestDTO CLIENT_REQUEST_DTO = new ClientRequestDTO(
            "Pedro Paiva",
            "sideral1@email.com",
            "381.118.240-41",
            "(85) 9000-0001"
    );

    public static final ClientResponseDTO CLIENT_RESPONSE_DTO = new ClientResponseDTO(
            1L,
            "Pedro Paiva",
            "sideral1@email.com",
            "381.118.240-41",
            "(85) 9000-0001"
    );

}
