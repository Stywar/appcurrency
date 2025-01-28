package com.globant.resource;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

import com.globant.model.CurrencyExchangeRequest;
import com.globant.model.CurrencyExchangeResponse;
import com.globant.service.CurrencyExchangeService;


@QuarkusTest
public class CurrencyExchangeResourceTest {

	@InjectMock
    CurrencyExchangeService service;

    @Test
    public void testConvertCurrency() {
    	
        CurrencyExchangeRequest request = new CurrencyExchangeRequest();
        request.setAmount(BigDecimal.valueOf(100));
        request.setSourceCurrency("USD");
        request.setTargetCurrency("EUR");

        CurrencyExchangeResponse expectedResponse = new CurrencyExchangeResponse(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(85), 
                "USD",
                "EUR",
                BigDecimal.valueOf(0.85)
        );

        Mockito.when(service.convertCurrency(any(CurrencyExchangeRequest.class)))
        .thenReturn(Uni.createFrom().item(expectedResponse));

        given()
                .contentType("application/json")
                .body(request)
        .when()
                .post("/api/exchange")
        .then()
                .statusCode(200)
                .body("originalAmount", is(100),
                      "convertedAmount", is(85),
                      "sourceCurrency", is("USD"),
                      "targetCurrency", is("EUR"),
                      "exchangeRate", is(0.85F));
    }
    
    @Test
    public void testConvertCurrency_InvalidRequest() {
        
        CurrencyExchangeRequest invalidRequest = new CurrencyExchangeRequest();
        invalidRequest.setAmount(BigDecimal.valueOf(-50));  
        invalidRequest.setSourceCurrency("XYZ");           
        invalidRequest.setTargetCurrency("EUR");

        Mockito.when(service.convertCurrency(any(CurrencyExchangeRequest.class)))
                .thenReturn(Uni.createFrom().failure(new IllegalArgumentException("Invalid input")));

     
        given()
            .contentType("application/json")
            .body(invalidRequest)
        .when()
            .post("/api/exchange")
        .then()
            .statusCode(400); 
    }
}