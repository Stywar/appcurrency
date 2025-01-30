package com.globant.repository;

import com.globant.entity.ExchangeRate;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExchangeRateRepository implements PanacheRepository<ExchangeRate> {

	public Uni<ExchangeRate> findByKey(String key) {
        return find("key", key).firstResult(); 
    }
	
}
