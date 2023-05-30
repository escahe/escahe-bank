package com.escahe.bank.dto.response;

import java.time.format.DateTimeFormatter;

import com.escahe.bank.model.Account;
import com.escahe.bank.model.Movement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MovementResponse {
    private String dateTime;
    private String movementAmount;
    private String accountBalance;
    private String accountNumber;
    private String clientName;

    public MovementResponse(Movement movement) {
        Account account = movement.getAccount();
        this.dateTime = movement.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.accountBalance = movement.getBalanceAfter().toString();
        this.movementAmount = movement.getAmount().toString();
        this.accountNumber = account.getNumber();
        this.clientName = account.getClient().getName();
    }
}
