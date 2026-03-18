package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.emuns.DiscountType;

import java.math.BigDecimal;

public record CouponValidationResponse(
        String code,
        DiscountType discountType,
        BigDecimal discountValue,
        BigDecimal discountAmount,
        BigDecimal finalAmount,
        String message
) {
}
