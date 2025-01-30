package com.globant.factory;

import java.math.BigDecimal;
import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;

public interface IResponseFactory {
    CurrencyExchangeResponse createResponse(CurrencyExchangeRequest request, BigDecimal convertedAmount, BigDecimal exchangeRate);
}
