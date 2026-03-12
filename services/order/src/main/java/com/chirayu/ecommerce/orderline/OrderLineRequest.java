package com.chirayu.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Long id,
        @NotNull(message = "Order is mandatory")
        Long orderId,
        @NotNull(message = "Product is mandatory")
        Long productId,
        @Positive(message = "Quantity is mandatory")
        double quantity
) {

}
