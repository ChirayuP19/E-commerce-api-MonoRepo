package com.chirayu.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is mandatory")
        Long productId,
        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity must be in positive")
        double quantity
) {
}
