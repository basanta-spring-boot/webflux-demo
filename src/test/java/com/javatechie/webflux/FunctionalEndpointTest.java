package com.javatechie.webflux;

import com.javatechie.webflux.dto.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
public class FunctionalEndpointTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void testGetCustomer(){
        Mono<Customer> customerMono = this.webClient
                .get()
                .uri("/router/customer/{input}", 4)
                .retrieve().bodyToMono(Customer.class);
       StepVerifier.create(customerMono)
                .expectNextMatches(c->c.getId()==4)
                .verifyComplete();
    }

    @Test
    public void testSaveCustomer(){
        Mono<String> mono = webClient.post().uri("/router/customer/save")
                .bodyValue(new Customer(10, "customer10"))
                .retrieve().bodyToMono(String.class);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
