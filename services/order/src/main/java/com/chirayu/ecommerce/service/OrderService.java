package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Long createOrder(@Valid OrderRequest orderRequest);

//    List<OrderResponse> findAll();

    Page<OrderResponse>findAll(int page,int size);

    OrderResponse findById(Long orderId);
}
