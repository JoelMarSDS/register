package com.cash.register.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private LocalDateTime dateRegister;

    @Column(length = 30)
    private String name;

    @Column(length = 14)
    private String cpf;

    @Column(length = 50)
    private String address;

    @Column(length = 40)
    private String city;

    @Column(length = 2)
    private String uf;

    @Column(length = 8)
    private String cep;

    @Column(length = 11)
    private String telephone;

    @Column(length = 100)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<CashBook> cashBooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CashBook> getCashBooks() {
        return cashBooks;
    }

    public void setCashBooks(List<CashBook> cashBooks) {
        this.cashBooks = cashBooks;
    }
}
