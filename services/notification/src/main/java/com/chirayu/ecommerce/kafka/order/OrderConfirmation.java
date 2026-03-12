package com.chirayu.ecommerce.kafka.order;

import com.chirayu.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customerResponse,
        List<Product> products
) {
}
