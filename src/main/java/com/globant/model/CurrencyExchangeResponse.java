package com.globant.model;

import java.math.BigDecimal;

public class CurrencyExchangeResponse {

    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal exchangeRate;

    public CurrencyExchangeResponse(BigDecimal originalAmount, BigDecimal convertedAmount, String sourceCurrency, String targetCurrency, BigDecimal exchangeRate) {
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
