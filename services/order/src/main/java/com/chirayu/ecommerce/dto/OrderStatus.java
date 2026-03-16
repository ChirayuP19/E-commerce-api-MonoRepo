package com.chirayu.ecommerce.dto;

public enum OrderStatus {
    PENDING,      // order created, waiting for payment
    CONFIRMED,    // payment successful
    PROCESSING,   // order being prepared
    SHIPPED,      // order shipped
    DELIVERED,    // order delivered
    CANCELLED
}
