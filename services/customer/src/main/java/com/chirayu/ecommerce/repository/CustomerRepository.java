package com.chirayu.ecommerce.repository;

import com.chirayu.ecommerce.customer.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    boolean existsByEmail(@NotNull(message = "Customer email is required") @Email(message = "Customer email is not valid email address") String email);
}
