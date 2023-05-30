package com.escahe.bank.model;

import java.util.ArrayList;
import java.util.List;

import com.escahe.bank.exception.client.ClientFieldsValidationException;
import com.escahe.bank.exception.client.ClientFieldsValidationException.ClientFieldExceptionMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "genero")
    private String genre;
    @Column(name = "edad")
    private Integer age;
    @Column(name = "identicacion")
    private String dni;
    @Column(name = "direccion")
    private String address;
    @Column(name = "telefono")
    private String phoneNumber;

    public Person(Long id, String name, String genre, Integer age, String dni, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private static void verifyName(String name) {
        List<ClientFieldExceptionMessage> messages = new ArrayList<>();
        if(name.isBlank())
            messages.add(ClientFieldExceptionMessage.NAME_BLANK_FIELD);
        if(!name.chars().allMatch(ch -> Character.isAlphabetic(ch) || ch == '\'' || ch == ' '))
            messages.add(ClientFieldExceptionMessage.NAME_WRONG_CHARACTER);
        if(!messages.isEmpty())
            throw new ClientFieldsValidationException(messages);
    }

    private static void verifyAge(int age) {
        List<ClientFieldExceptionMessage> messages = new ArrayList<>();
        if(age < 14)
            messages.add(ClientFieldExceptionMessage.AGE_MINIMUN_REQUIRED);
        if(!messages.isEmpty())
            throw new ClientFieldsValidationException(messages);
    }
    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        verifyName(name);
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        verifyAge(age);
        this.age = age;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", dni=" + dni + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
        Person other = (Person) obj;
        if (dni == null) {
            if (other.dni != null)
                return false;
        } else if (!dni.equals(other.dni))
            return false;
        return true;
    }

}
