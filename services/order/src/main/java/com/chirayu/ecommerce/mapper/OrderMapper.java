package com.chirayu.ecommerce.mapper;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest,String reference, BigDecimal totalAmount){
        return Order.builder()
                .id(orderRequest.id())
                .reference(reference)
                .totalAmount(totalAmount)
                .paymentMethod(orderRequest.paymentMethod())
                .customerId(orderRequest.customerId())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
