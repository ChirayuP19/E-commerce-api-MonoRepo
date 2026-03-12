package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.PaymentRequest;
import jakarta.validation.Valid;

public interface PaymentService {
    Long createPayment(@Valid PaymentRequest request);
}
