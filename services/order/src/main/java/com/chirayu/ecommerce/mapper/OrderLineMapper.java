package com.chirayu.ecommerce.mapper;

import com.chirayu.ecommerce.entity.Order;
import com.chirayu.ecommerce.orderline.OrderLine;
import com.chirayu.ecommerce.orderline.OrderLineRequest;
import com.chirayu.ecommerce.orderline.OrderLineResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                ).productId(request.productId())
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
