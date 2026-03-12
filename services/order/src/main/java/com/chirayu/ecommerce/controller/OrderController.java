package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chirayu.ecommerce.service.OrderService;

import java.util.List;

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
    public ResponseEntity<List<OrderResponse>> findAll(){
        return new ResponseEntity<>(service.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById
            (@PathVariable("order-id") Long orderId){
        return new ResponseEntity<>(service.findById(orderId),HttpStatus.OK);
    }

}
