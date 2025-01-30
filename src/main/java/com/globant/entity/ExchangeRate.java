package com.globant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
public class ExchangeRate extends PanacheEntityBase {

    @Id
    @Column(name = "currency_pair_key")
    private String key;
    private BigDecimal rate;
   
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
