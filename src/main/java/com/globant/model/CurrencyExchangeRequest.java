package com.globant.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CurrencyExchangeRequest {

    private BigDecimal amount;
    private String sourceCurrency;
    private String targetCurrency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyExchangeRequest that = (CurrencyExchangeRequest) o;
        return Objects.equals(sourceCurrency, that.sourceCurrency) &&
               Objects.equals(targetCurrency, that.targetCurrency) &&
               Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCurrency, targetCurrency, amount);
    }
}