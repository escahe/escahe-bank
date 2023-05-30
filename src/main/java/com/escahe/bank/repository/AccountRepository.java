package com.escahe.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escahe.bank.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByNumber(String number);
    boolean existsByNumber(String number);
}
