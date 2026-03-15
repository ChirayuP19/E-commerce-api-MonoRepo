package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.PaymentRequest;
import com.chirayu.ecommerce.dto.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface PaymentService {
    Long createPayment(@Valid PaymentRequest request);
    Page<PaymentResponse> findAll(int page, int size);
    PaymentResponse findById(Long paymentId);
}
