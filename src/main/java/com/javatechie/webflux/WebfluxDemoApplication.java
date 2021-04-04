package com.javatechie.webflux;

import com.javatechie.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/customers")
public class WebfluxDemoApplication {

	@Autowired
	private CustomerService service;

	@GetMapping
	public List<Customer> getCustomers(){
		return service.loadCustomers();
	}


	@GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getCustomersWithStream(){
		return service.loadCustomersStream();
	}


	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

}
