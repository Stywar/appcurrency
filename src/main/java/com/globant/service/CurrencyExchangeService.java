package com.globant.service;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.jboss.logging.Logger;

import com.globant.factory.ResponseFactory;
import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;
import com.globant.provider.ExchangeRateProvider;

import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
@ApplicationScoped
public class CurrencyExchangeService {

	private static final Logger LOG = Logger.getLogger(CurrencyExchangeService.class);
	
    private final ExchangeRateProvider exchangeRateProvider;
    private final ResponseFactory responseFactory;

    public CurrencyExchangeService(ExchangeRateProvider exchangeRateProvider, ResponseFactory responseFactory) {
        this.exchangeRateProvider = exchangeRateProvider;
        this.responseFactory = responseFactory;
    }

    @CacheResult(cacheName = "currency-cache")
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000)
    public Uni<CurrencyExchangeResponse> convertCurrency(@CacheKey CurrencyExchangeRequest request) {
    	LOG.infof("Processing currency exchange for: %s -> %s, Amount: %s", 
                request.getSourceCurrency(), 
                request.getTargetCurrency(), 
                request.getAmount());
    	
    	 return Uni.createFrom().item(() -> {
    	        BigDecimal exchangeRate = exchangeRateProvider.getExchangeRate(
    	                request.getSourceCurrency(), 
    	                request.getTargetCurrency()
    	        );
    	        BigDecimal convertedAmount = request.getAmount().multiply(exchangeRate);

    	        LOG.info("Exchange rate calculated and not from cache.");
    	        return responseFactory.createResponse(request, convertedAmount, exchangeRate);
    	    });
    }
}