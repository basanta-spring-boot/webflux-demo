package com.javatechie.webflux.handler;

import com.javatechie.webflux.dao.CustomerDao;
import com.javatechie.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> findCustomerById(ServerRequest request){
        int customerId=Integer.valueOf(request.pathVariable("input"));
//        Mono<Customer> customerMono = dao.getCustomersWithStream()
//                .filter(customer -> customer.getId() == id).take(1).single();
        Mono<Customer> customer = dao.loadCustomers()
                .filter(c -> c.getId() == customerId).next();
        return ServerResponse.ok().body(customer,Customer.class);
    }

    public Mono<ServerResponse> findAllCustomers(ServerRequest request){
        Flux<Customer> customerFlux = dao.loadCustomers();
        return ServerResponse.ok().body(customerFlux,Customer.class);
    }

    public Mono<ServerResponse> findAllCustomersStream(ServerRequest request){
        Flux<Customer> customerFlux = dao.getCustomersWithStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerFlux,Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> stringMono = dao.saveCustomer(customerMono);
        return ServerResponse.ok()
                .body(stringMono,String.class);
    }
}
