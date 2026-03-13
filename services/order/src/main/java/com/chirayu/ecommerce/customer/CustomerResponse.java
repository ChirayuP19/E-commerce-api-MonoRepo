package com.chirayu.ecommerce.customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
