package com.javatechie.webflux;

import com.javatechie.webflux.dto.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


public class ReactiveEndpointTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void testAsyncFlow(){
        Flux<Customer> customerFlux = webClient.get().uri("/customers/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve().bodyToFlux(Customer.class)
                .log();

        StepVerifier.create(customerFlux)
                .expectNextMatches(c->c.getId()==1)
                .expectNext(new Customer(2,"customer2"))
                .thenCancel()
                .verify();
    }
}
