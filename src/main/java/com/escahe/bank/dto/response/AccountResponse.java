package com.escahe.bank.dto.response;

import java.math.BigDecimal;

import com.escahe.bank.model.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String number;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    private Long clientId;
    
    public AccountResponse(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.type = account.getType();
        this.balance = account.getBalance();
        this.status = account.getStatus();
        this.clientId = account.getClient().getId();
    }
}
