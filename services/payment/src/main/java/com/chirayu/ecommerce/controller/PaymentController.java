package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.PaymentRequest;
import com.chirayu.ecommerce.dto.PaymentResponse;
import com.chirayu.ecommerce.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(paymentService.findAll(page, size));
    }

    @GetMapping("/{payment-id}")
    public ResponseEntity<PaymentResponse> findById(
            @PathVariable("payment-id") Long paymentId) {
        return ResponseEntity.ok(paymentService.findById(paymentId));
    }
}
