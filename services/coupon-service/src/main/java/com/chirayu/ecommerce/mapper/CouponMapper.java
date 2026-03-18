package com.chirayu.ecommerce.mapper;

import com.chirayu.ecommerce.dto.CouponRequest;
import com.chirayu.ecommerce.dto.CouponResponse;
import com.chirayu.ecommerce.entity.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public Coupon toCoupon(CouponRequest request){
        return Coupon.builder()
                .code(request.code().toUpperCase())
                .description(request.description())
                .discountType(request.discountType())
                .discountValue(request.discountValue())
                .minimumOrderAmount(request.minimumOrderAmount())
                .usageLimit(request.usageLimit())
                .usageCount(0)
                .expiryDate(request.expiryDate())
                .active(true)
                .build();
    }

    public CouponResponse fromCoupon(Coupon coupon){
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getMinimumOrderAmount(),
                coupon.getUsageLimit(),
                coupon.getUsageCount(),
                coupon.getExpiryDate(),
                coupon.getActive(),
                coupon.getCreatedAt()
        );
    }
}
