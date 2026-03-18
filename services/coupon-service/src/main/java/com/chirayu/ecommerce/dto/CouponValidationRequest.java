package com.chirayu.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CouponValidationRequest(
        @NotBlank(message = "Coupon code is required")
        String code,
        @NotNull(message = "Order amount is required")
        BigDecimal orderAmount
) {
}
