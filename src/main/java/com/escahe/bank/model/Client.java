package com.escahe.bank.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Client extends Person {
    @Column(name = "contraseña")
    private String password;
    @Column(name = "estado")
    private Boolean status;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Client(Long id, String name, String genre, Integer age, String dni, String address, String phoneNumer,
    String password, Boolean status) {
        super(id, name, genre, age, dni, address, phoneNumer);
        this.password = password;
        this.status = status;
    }

    public Client() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

