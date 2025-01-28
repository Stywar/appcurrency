package com.globant.provider;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExchangeRateProvider {

    private final Map<String, BigDecimal> exchangeRates;

    public ExchangeRateProvider() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("USD_EUR", BigDecimal.valueOf(0.85));
        rates.put("EUR_USD", BigDecimal.valueOf(1.18));
        rates.put("USD_PEN", BigDecimal.valueOf(3.78));
        rates.put("PEN_USD", BigDecimal.valueOf(0.26));
        this.exchangeRates = Collections.unmodifiableMap(rates);
    }

    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {
        String key = sourceCurrency + "_" + targetCurrency;
        BigDecimal rate = exchangeRates.get(key);
        if (rate == null) {
            throw new IllegalArgumentException("Tipo de cambio no encontrado para " + key);
        }
        return rate;
    }
}