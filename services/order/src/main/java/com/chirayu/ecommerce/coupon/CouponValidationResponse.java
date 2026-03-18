package com.chirayu.ecommerce.coupon;

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
