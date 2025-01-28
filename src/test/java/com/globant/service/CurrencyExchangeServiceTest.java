package com.globant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.globant.model.CurrencyExchangeRequest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CurrencyExchangeServiceTest {

	    @Inject
	    CurrencyExchangeService currencyExchangeService;

	    @Test
	    public void testCacheBehavior() {
	        CurrencyExchangeRequest request = new CurrencyExchangeRequest();
	        request.setAmount(new BigDecimal("100"));
	        request.setSourceCurrency("USD");
	        request.setTargetCurrency("EUR");

	        // Primera llamada (calcula y guarda en la caché)
	        currencyExchangeService.convertCurrency(request)
	                .subscribe().with(response -> {
	                    assertNotNull(response);
	                    assertEquals("USD", response.getSourceCurrency());
	                    assertEquals("EUR", response.getTargetCurrency());
	                });

	        // Segunda llamada (debe usar caché)
	        currencyExchangeService.convertCurrency(request)
	                .subscribe().with(response -> {
	                    assertNotNull(response);
	                    assertEquals("USD", response.getSourceCurrency());
	                    assertEquals("EUR", response.getTargetCurrency());
	                });
	    }
	}
