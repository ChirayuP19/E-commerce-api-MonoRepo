package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.dto.CouponRequest;
import com.chirayu.ecommerce.dto.CouponResponse;
import com.chirayu.ecommerce.dto.CouponValidationRequest;
import com.chirayu.ecommerce.dto.CouponValidationResponse;
import org.springframework.data.domain.Page;

public interface CouponService {
    CouponResponse createCoupon(CouponRequest couponRequest);
    CouponValidationResponse validateCoupon(CouponValidationRequest request);
    CouponResponse findByCode(String code);
    Page<CouponResponse> findAll(int page, int size);
    CouponResponse deactivateCoupon(Long id);
    void deleteCoupon(Long id);
}
