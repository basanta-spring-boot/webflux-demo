package com.javatechie.webflux;

import com.javatechie.webflux.dao.CustomerDao;
import com.javatechie.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> loadCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customers = customerDao.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Total time taken for execution : " + (end - start));
        return customers;
    }

    public Flux<Customer> loadCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = customerDao.getCustomersWithStream();
        long end = System.currentTimeMillis();
        System.out.println("Total time taken for execution : " + (end - start));
        return customers;
    }

    public Mono<String> saveCustomer(Mono<Customer> customerMono) {
        return customerDao.saveCustomer(customerMono);
    }
}
