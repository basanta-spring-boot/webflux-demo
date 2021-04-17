package com.javatechie.webflux.router;

import com.javatechie.webflux.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customer/{input}", customerHandler::findCustomerById)
                .GET("/router/findAllCustomers",customerHandler::findAllCustomers)
                .GET("/router/findAllCustomers/stream",customerHandler::findAllCustomersStream)
                .POST("/router/customer/save",customerHandler::saveCustomer)
                .build();
    }
}
