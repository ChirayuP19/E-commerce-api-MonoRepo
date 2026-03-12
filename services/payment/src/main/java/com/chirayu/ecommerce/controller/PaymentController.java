package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.PaymentRequest;
import com.chirayu.ecommerce.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> createPayment(
            @RequestBody @Valid PaymentRequest request){
        return new ResponseEntity<>(paymentService.createPayment(request), HttpStatus.CREATED);

    }
}
