package com.globant.resource;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import com.globant.model.CurrencyExchangeRequest;
import com.globant.service.CurrencyExchangeService;
import com.globant.service.ICurrencyExchangeService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/exchange")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyExchangeResource {

	@Inject
    ICurrencyExchangeService currencyExchangeService;

    @POST
    @Retry(maxRetries = 3)
    @Timeout(value = 2, unit = java.time.temporal.ChronoUnit.SECONDS)
    public Uni<Response> convertCurrency(CurrencyExchangeRequest request) {
        return currencyExchangeService.convertCurrency(request)
                .onItem().transform(response -> Response.ok(response).build())
                .onFailure(IllegalArgumentException.class)
                .recoverWithItem(e -> Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build()); 
    }
}