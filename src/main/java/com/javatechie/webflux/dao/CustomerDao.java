package com.javatechie.webflux.dao;

import com.javatechie.webflux.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    private static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers()  {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao::sleepExecution)
                .peek(i -> System.out.println("Processing count : " + i))
                .mapToObj(i -> new Customer(i, "customer" + i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersWithStream()  {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count in stream: " + i))
                .map(i -> new Customer(i, "customer" + i));
    }

    public Flux<Customer> loadCustomers()  {
        return Flux.range(1, 10)
                .map(i -> new Customer(i, "customer" + i));
    }

    public Mono<String> saveCustomer(Mono<Customer> customerMono) {
        return customerMono.map(dto-> dto.getId() + " : " + dto.getName());
    }



}
