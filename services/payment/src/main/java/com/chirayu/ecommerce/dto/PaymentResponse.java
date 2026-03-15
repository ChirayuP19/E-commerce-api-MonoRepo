package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.payload.PaymentMethod;

import java.math.BigDecimal;

public record PaymentResponse(
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference
) {
}
