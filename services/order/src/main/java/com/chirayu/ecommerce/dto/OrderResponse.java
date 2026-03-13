package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.payload.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
