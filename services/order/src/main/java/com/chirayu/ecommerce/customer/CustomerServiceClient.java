package com.chirayu.ecommerce.customer;

import com.chirayu.ecommerce.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceClient {
    private final CustomerClient customerClient;

    @CircuitBreaker(name = "customer-service", fallbackMethod = "customerFallback")
    public CustomerResponse findCustomerById(String customerId) {
        var customer = customerClient.findCustomerFindById(customerId);
        if (customer == null) {
            throw new BusinessException(
                    "Cannot create order:: No Customer exists with the provided ID:: " + customerId);
        }
        return customer;
    }

    public CustomerResponse customerFallback(String customerId, Throwable ex) {
        throw new BusinessException(
                "Cannot create order:: Customer service is currently unavailable. Please try again later.");
    }
}
