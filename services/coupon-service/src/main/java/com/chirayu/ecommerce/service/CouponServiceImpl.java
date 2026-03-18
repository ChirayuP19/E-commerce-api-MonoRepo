package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.dto.CouponRequest;
import com.chirayu.ecommerce.dto.CouponResponse;
import com.chirayu.ecommerce.dto.CouponValidationRequest;
import com.chirayu.ecommerce.dto.CouponValidationResponse;
import com.chirayu.ecommerce.emuns.DiscountType;
import com.chirayu.ecommerce.entity.Coupon;
import com.chirayu.ecommerce.exception.CouponAlreadyUsedException;
import com.chirayu.ecommerce.exception.CouponExpiredException;
import com.chirayu.ecommerce.exception.CouponNotFoundException;
import com.chirayu.ecommerce.mapper.CouponMapper;
import com.chirayu.ecommerce.repository.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper mapper;

    @Override
    @Transactional
    public CouponResponse createCoupon(CouponRequest couponRequest) {
        if (couponRepository.existsByCode(couponRequest.code().toUpperCase())) {
            throw new RuntimeException(
                    "Coupon code already exists: " + couponRequest.code()
            );
        }
        var coupon = couponRepository.save(mapper.toCoupon(couponRequest));
        return mapper.fromCoupon(coupon);
    }

    @Override
    @Transactional
    public CouponValidationResponse validateCoupon(CouponValidationRequest request) {
        var coupon = couponRepository.findByCode(request.code().toUpperCase())
                .orElseThrow(() -> new CouponNotFoundException(
                        "Coupon not found: " + request.code()));
        if (!coupon.getActive()) {
            throw new CouponAlreadyUsedException(
                    "Coupon is no longer active: " + request.code());
        }
        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CouponExpiredException(
                    "Coupon has expired: " + request.code());
        }
        if (coupon.getUsageLimit() != null && coupon.getUsageCount() >= coupon.getUsageLimit()) {
            throw new CouponAlreadyUsedException(
                    "Coupon usage limit reached: " + request.code());
        }
        if (coupon.getMinimumOrderAmount() != null && request.orderAmount().compareTo(coupon.getMinimumOrderAmount()) < 0) {
            throw new RuntimeException(
                    "Order amount must be at least " + coupon.getMinimumOrderAmount() + " to use this coupon");
        }

        BigDecimal discountAmount = calculateDiscount(
                coupon, request.orderAmount());
        BigDecimal finalAmount = request.orderAmount().subtract(discountAmount)
                .max(BigDecimal.ZERO);

        coupon.setUsageCount(coupon.getUsageCount()+1);

        if(coupon.getUsageLimit()!= null && coupon.getUsageCount()>=coupon.getUsageLimit()){
            coupon.setActive(false);
        }
        couponRepository.save(coupon);

        return new CouponValidationResponse(
                coupon.getCode(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                discountAmount,
                finalAmount,
                "Coupon applied successfully! You saved " + discountAmount
        );
    }

    @Override
    public CouponResponse findByCode(String code) {
        return couponRepository.findByCode(code.toUpperCase())
                .map(mapper::fromCoupon)
                .orElseThrow(() -> new CouponNotFoundException(
                        "Coupon not found: " + code));
    }

    @Override
    public Page<CouponResponse> findAll(int page, int size) {
        var pageable= PageRequest.of(page,size, Sort.by("id").ascending());
        return couponRepository.findAll(pageable).map(mapper::fromCoupon);
    }

    @Override
    @Transactional
    public CouponResponse deactivateCoupon(Long id) {
        var coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Coupon not found with ID: " + id));
        coupon.setActive(false);
        return mapper.fromCoupon(couponRepository.save(coupon));
    }

    @Override
    public void deleteCoupon(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Coupon not found with ID: " + id);
        }
        couponRepository.deleteById(id);
    }

    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount){
        if(coupon.getDiscountType() == DiscountType.PERCENTAGE){
            return orderAmount
                    .multiply(coupon.getDiscountValue())
                    .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        }else {
            return coupon.getDiscountValue().min(orderAmount);
        }
    }
}
