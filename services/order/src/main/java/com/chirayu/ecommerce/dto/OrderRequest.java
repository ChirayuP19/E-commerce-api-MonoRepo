package com.chirayu.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.chirayu.ecommerce.payload.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be present")
        @NotEmpty(message = "Customer should be present")
        @NotBlank(message = "Customer should be present")
        String customerId,
        @NotEmpty(message = "Should be purchase at least on product")
        List<PurchaseRequest> products
) {
}
