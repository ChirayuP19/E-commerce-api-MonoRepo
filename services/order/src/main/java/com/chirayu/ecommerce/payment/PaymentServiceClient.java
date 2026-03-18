package com.chirayu.ecommerce.payment;

import com.chirayu.ecommerce.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceClient {
    private final PaymentClient paymentClient;


    @CircuitBreaker(name = "payment-cb", fallbackMethod = "paymentFallback")
    public Long requestPayment(PaymentRequest paymentRequest){
        return paymentClient.requestOrderPayment(paymentRequest);
    }

    public Long paymentFallback(PaymentRequest paymentRequest,Throwable ex){
        throw new BusinessException("Payment Service is currently unavailable. Please try again later.");
    }
}
