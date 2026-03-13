package com.chirayu.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
