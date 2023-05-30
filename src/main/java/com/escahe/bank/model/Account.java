package com.escahe.bank.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.escahe.bank.exception.account.AccountFieldsValidationException;
import com.escahe.bank.exception.account.AccountFieldsValidationException.AccountFieldExceptionMessage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "numero_cuenta")
    private String number;
    @Column(name = "tipo_cuenta")
    private String type;
    @Column(name = "saldo")
    private BigDecimal balance;
    @Column(name = "estado")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "cliente")
    private Client client;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Movement> movements;

    public Account() {
    }

    public Account(Long id, String number, String type, BigDecimal balance, Boolean status, Client client) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.balance = balance;
        this.status = status;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        List<AccountFieldExceptionMessage> errorMessages = new ArrayList<>();
        if(!number.chars().allMatch(digit -> Character.isDigit(digit)))
            errorMessages.add(AccountFieldExceptionMessage.INVALID_NUMBER_FORMAT);
        if(number.length() != 10)
            errorMessages.add(AccountFieldExceptionMessage.INVALID_NUMBER_LENGTH);
        if(!errorMessages.isEmpty()) throw new AccountFieldsValidationException(errorMessages);
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type.toLowerCase();
        type = type.contains("ahorro")? "ahorro": type.contains("corriente")? "corriente": "#";
        if(type.equals("#"))
            throw new AccountFieldsValidationException(List.of(AccountFieldExceptionMessage.INVALID_TYPE));
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Account [number=" + number + ", client=" + client + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }

}


