package com.chirayu.ecommerce.dto;

import com.chirayu.ecommerce.customer.Address;

public record CustomerResponse (
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
){
}
