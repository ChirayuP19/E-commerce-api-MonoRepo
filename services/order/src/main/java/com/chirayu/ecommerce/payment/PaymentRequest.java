package com.chirayu.ecommerce.payment;

import com.chirayu.ecommerce.customer.CustomerResponse;
import com.chirayu.ecommerce.payload.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        CustomerResponse customer
) {
}
