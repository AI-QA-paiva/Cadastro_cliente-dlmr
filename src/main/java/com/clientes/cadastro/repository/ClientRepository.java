package com.clientes.cadastro.repository;

import com.clientes.cadastro.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // TODO: Import necessário caso decida retornar uma lista de clientes com o mesmo nome.
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // TODO: Avaliar se o método `findByName` deve retornar um Optional ou uma lista.
    // Se o nome for único, o Optional é suficiente. Caso contrário, considere retornar uma lista.
    Optional<Client> findByName(String name);

    // TODO: Caso o nome não seja único, descomente o método abaixo e remova o `findByName` para evitar confusão.
    // List<Client> findByName(String name);

    // TODO: Considere adicionar métodos de busca mais específicos, como `findByEmail` ou `findByDocument`,
    // caso sejam necessários para atender a requisitos futuros.
}