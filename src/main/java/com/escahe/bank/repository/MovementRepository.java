package com.escahe.bank.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.escahe.bank.model.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long>{

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM Movement m WHERE m.account.number = :accountNumber AND CAST(m.date AS date) = CURRENT_DATE AND m.amount < 0")
    BigDecimal totalWithdrawnTodayByAccount(String accountNumber);

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM Movement m WHERE m.account.number = :accountNumber AND m.date >= :startDate AND m.date < :endDate AND m.amount < 0")
    BigDecimal totalWithdrawnByAccountAndDates(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM Movement m WHERE m.account.number = :accountNumber AND m.date >= :startDate AND m.date < :endDate AND m.amount > 0")
    BigDecimal totalDepositedByAccountAndDates(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Movement> findByAccountNumberAndDateBetween(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
    List<Movement> findByAccountClientIdAndDateBetween(Long clientId, LocalDateTime startDate, LocalDateTime endDate);
}

