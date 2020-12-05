package com.cash.register.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CashBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private LocalDate lancetDate;

    @Column(length = 50)
    private String description;

    @Column(length = 1)
    private Character type;

    @Column(precision = 12, scale = 2)
    private BigDecimal value;

    @ManyToOne//(cascade = )
    @JoinColumn
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getLancetDate() {
        return lancetDate;
    }

    public void setLancetDate(LocalDate lancetDate) {
        this.lancetDate = lancetDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
