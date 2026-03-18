package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.emuns.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String code,
        String description,
        DiscountType discountType,
        BigDecimal discountValue,
        BigDecimal minimumOrderAmount,
        Integer usageLimit,
        Integer usageCount,
        LocalDateTime expiryDate,
        Boolean active,
        LocalDateTime createdAt
) {
}
