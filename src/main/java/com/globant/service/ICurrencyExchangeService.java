package com.globant.service;

import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;
import io.smallrye.mutiny.Uni;

public interface ICurrencyExchangeService {
    Uni<CurrencyExchangeResponse> convertCurrency(CurrencyExchangeRequest request);
}
