package com.chirayu.ecommerce.coupon;

import java.math.BigDecimal;

public record CouponValidationRequest(
        String code,
        BigDecimal orderAmount
) {
}
