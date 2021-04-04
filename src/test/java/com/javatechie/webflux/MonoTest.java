package com.javatechie.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

//    @Test
    public void testMono() {
        Mono<?> youTubeName = Mono
                .just("javatechie")
                .then(Mono.error(new RuntimeException("Forcefully error occurred !")))
                .log();
        youTubeName
                .subscribe(System.out::println,
                        (e) -> System.err.println(e.getMessage()));
    }

    @Test
    public void testFlux(){
        Flux<String> frameworks = Flux
                .just("Spring", "Spring Boot", "Hibernate")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Exception in flux")))
                .concatWith(Flux.just("after error"))
                .log();

        frameworks.subscribe(System.out::println,(e)->System.err.println(e.getMessage()));
    }


}
