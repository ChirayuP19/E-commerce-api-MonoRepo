package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstname is required")
        @Size(min = 2,max = 50)
        String firstname,
        @NotNull(message = "Customer lastname is required")
        @Size(min = 2,max = 50)
        String lastname,
        @NotNull(message = "Customer email is required")
        @Email(message = "Customer email is not valid email address")
        String email,
        Address address
) {
}
