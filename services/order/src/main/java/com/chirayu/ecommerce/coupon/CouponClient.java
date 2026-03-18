package com.chirayu.ecommerce.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "coupon-service")
public interface CouponClient {

    @PostMapping("/api/v1/coupons/validate")
    CouponValidationResponse validateCoupon(
            @RequestBody CouponValidationRequest request);
}
