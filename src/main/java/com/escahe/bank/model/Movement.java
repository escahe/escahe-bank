package com.escahe.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos")
public class Movement {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "fecha")
    private LocalDateTime date;
    @Column(name = "movimiento")
    private BigDecimal amount;
    @Column(name = "saldo_cuenta")
    private BigDecimal balanceAfter;
    @ManyToOne
    @JoinColumn(name = "cuenta")
    private Account account;

    public Movement(){}

    public Movement(LocalDateTime date, BigDecimal amount, BigDecimal balanceAfter, Account account) {
        this.date = date;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.account = account;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }
    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((account == null) ? 0 : account.hashCode());
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
        Movement other = (Movement) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Movement [id=" + id + ", date=" + date + ", amount=" + amount + ", balanceAfter=" + balanceAfter
                + ", account=" + account.getNumber() + "]";
    }

}
