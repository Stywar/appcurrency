package com.globant.service;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
//import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.jboss.logging.Logger;

import com.globant.entity.ExchangeRate;
import com.globant.factory.IResponseFactory;
import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;
import com.globant.repository.ExchangeRateRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@ApplicationScoped
public class CurrencyExchangeService implements ICurrencyExchangeService {

    private static final Logger LOG = Logger.getLogger(CurrencyExchangeService.class);
    @Inject
    private IResponseFactory responseFactory;
       
    @Inject
    ExchangeRateRepository exchangeRateRepository;

    
    @WithSession
    @CacheResult(cacheName = "currency-cache")
    //@CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000)
    @Override    
    public Uni<CurrencyExchangeResponse> convertCurrency(@CacheKey CurrencyExchangeRequest request) {
        LOG.infof("Processing currency exchange for: %s -> %s, Amount: %s", 
                  request.getSourceCurrency(), 
                  request.getTargetCurrency(), 
                  request.getAmount());        
        
        String key = request.getSourceCurrency() + "_" + request.getTargetCurrency();

        return exchangeRateRepository.findByKey(key)
                .onItem().ifNull().failWith(new IllegalArgumentException("Tipo de cambio no encontrado para " + key))
                .map(ExchangeRate::getRate)
                .flatMap(exchangeRate -> {
                    BigDecimal convertedAmount = request.getAmount().multiply(exchangeRate);
                    LOG.info("Exchange rate calculated and not from cache.");
                    return Uni.createFrom().item(() -> responseFactory.createResponse(request, convertedAmount, exchangeRate));
                });
    }
}
