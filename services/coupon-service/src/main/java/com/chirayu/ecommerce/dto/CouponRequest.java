package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.emuns.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequest(
        @NotBlank(message = "Coupon code is required")
        String code,
        String description,
        @NotNull(message = "Discount type is required")
        DiscountType discountType,
        @NotNull(message = "Discount value is required")
        @Positive(message = "Discount value must be positive")
        BigDecimal discountValue,
        BigDecimal minimumOrderAmount,
        Integer usageLimit,
        @NotNull(message = "Expiry date is required")
        LocalDateTime expiryDate
) {
}
