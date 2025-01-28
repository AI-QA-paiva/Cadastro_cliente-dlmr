package com.clientes.cadastro.repository;

import com.clientes.cadastro.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

}

/* ESTUDAR O CASO: >> com pesquisar por nome que pode me trzer uma lista porque tem várias pessoas com um nome em comum???*/
//List<Client> findByName(String name);