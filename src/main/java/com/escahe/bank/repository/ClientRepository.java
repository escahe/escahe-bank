package com.escahe.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escahe.bank.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByDni(String dni);
}
