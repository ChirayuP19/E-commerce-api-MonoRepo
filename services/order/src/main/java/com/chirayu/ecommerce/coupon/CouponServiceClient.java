package com.chirayu.ecommerce.coupon;

import com.chirayu.ecommerce.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CouponServiceClient {
    private final CouponClient couponClient;

    @CircuitBreaker(name = "coupon-cb", fallbackMethod = "couponFallback")
    public CouponValidationResponse validateCoupon(String code, BigDecimal orderAmount){
        return couponClient.validateCoupon(new CouponValidationRequest(code,orderAmount));
    }

    public CouponValidationResponse couponFallback(
            String code, BigDecimal orderAmount, Throwable ex) {
        throw new BusinessException(
                "Coupon service is currently unavailable. Please try again later.");
    }
}
