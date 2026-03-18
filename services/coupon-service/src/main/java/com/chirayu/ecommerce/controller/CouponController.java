package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.CouponRequest;
import com.chirayu.ecommerce.dto.CouponResponse;
import com.chirayu.ecommerce.dto.CouponValidationRequest;
import com.chirayu.ecommerce.dto.CouponValidationResponse;
import com.chirayu.ecommerce.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(
            @RequestBody @Valid CouponRequest request){
        return new ResponseEntity<>(couponService.createCoupon(request), HttpStatus.CREATED);
    }

    @PostMapping("/validate")
    public ResponseEntity<CouponValidationResponse> validateCoupon(
            @RequestBody @Valid CouponValidationRequest validationRequest){
        return ResponseEntity.ok(couponService.validateCoupon(validationRequest));
    }

    @GetMapping("/{code}")
    public ResponseEntity<CouponResponse> findByCode(
            @PathVariable String code){
        return ResponseEntity.ok(couponService.findByCode(code));
    }

    @GetMapping
    public ResponseEntity<Page<CouponResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(couponService.findAll(page, size));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CouponResponse> deactivateCoupon(
            @PathVariable Long id) {
        return ResponseEntity.ok(couponService.deactivateCoupon(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(
            @PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
