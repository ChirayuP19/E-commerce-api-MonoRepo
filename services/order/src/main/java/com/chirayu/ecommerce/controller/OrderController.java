package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.dto.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chirayu.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Long> createOrder(
            @RequestBody @Valid OrderRequest orderRequest){
        return new ResponseEntity<>(service.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById
            (@PathVariable("order-id") Long orderId){
        return new ResponseEntity<>(service.findById(orderId),HttpStatus.OK);
    }

    @PatchMapping("/{order-id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable("order-id")Long orderId,
            @RequestParam OrderStatus status){
        return new ResponseEntity<>(service.updateOrderStatus(orderId,status),HttpStatus.OK);
    }

}
