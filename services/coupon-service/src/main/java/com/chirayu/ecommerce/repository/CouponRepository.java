package com.chirayu.ecommerce.repository;

import com.chirayu.ecommerce.entity.Coupon;
import org.bouncycastle.pqc.legacy.crypto.rainbow.Layer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    boolean existsByCode(String code);
}
