package com.globant.factory;

import java.math.BigDecimal;
import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResponseFactory implements IResponseFactory {

    @Override
    public CurrencyExchangeResponse createResponse(CurrencyExchangeRequest request, BigDecimal convertedAmount, BigDecimal exchangeRate) {
        return new CurrencyExchangeResponse(
                request.getAmount(),
                convertedAmount,
                request.getSourceCurrency(),
                request.getTargetCurrency(),
                exchangeRate
        );
    }
}
