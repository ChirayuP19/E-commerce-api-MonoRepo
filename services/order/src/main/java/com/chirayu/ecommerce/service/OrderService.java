package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    Long createOrder(@Valid OrderRequest orderRequest);

    List<OrderResponse> findAll();

    OrderResponse findById(Long orderId);
}
