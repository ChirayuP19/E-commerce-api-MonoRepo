package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.dto.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface OrderService {
    Long createOrder(@Valid OrderRequest orderRequest);

    Page<OrderResponse>findAll(int page,int size);

    OrderResponse findById(Long orderId);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
