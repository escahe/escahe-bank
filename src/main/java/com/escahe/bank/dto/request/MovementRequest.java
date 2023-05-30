package com.escahe.bank.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MovementRequest {
    private Long clientId;
    private String accountNumber;
    private BigDecimal amount;
}
