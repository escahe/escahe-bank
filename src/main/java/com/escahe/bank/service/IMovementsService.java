package com.escahe.bank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.escahe.bank.model.Movement;

public interface IMovementsService {
    Movement deposit(String accountNumber, BigDecimal amount);
    Movement withdraw(String accountNumber, BigDecimal amount);

    List<Movement> getAllMovementsByClientIdBetweenDates(Long clientId, LocalDate startDate, LocalDate endDate);
}

