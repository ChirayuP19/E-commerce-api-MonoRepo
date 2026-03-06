package com.chirayu.ecommerce.customer;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Customer {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private Address address;
}
