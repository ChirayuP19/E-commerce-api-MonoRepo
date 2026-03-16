package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.payload.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId,
        OrderStatus status,
        LocalDateTime createTime,
        LocalDateTime lastModifiedDate
) {
}
