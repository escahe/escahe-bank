package com.escahe.bank.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountRequest {
    private Long id;
    private String number;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    private Long clientId;
}