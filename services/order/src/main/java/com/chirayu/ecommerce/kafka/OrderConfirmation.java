package com.chirayu.ecommerce.kafka;

import com.chirayu.ecommerce.customer.CustomerResponse;
import com.chirayu.ecommerce.payload.PaymentMethod;
import com.chirayu.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
