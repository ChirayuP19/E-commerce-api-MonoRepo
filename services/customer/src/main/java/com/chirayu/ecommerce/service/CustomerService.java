package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.customer.CustomerDocument;
import com.chirayu.ecommerce.dto.CustomerRequest;
import com.chirayu.ecommerce.dto.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    String createCustomer(@Valid CustomerRequest request);

    void updateCustomer(@Valid CustomerRequest request);

//    List<CustomerResponse> findAllCustomer();

    Boolean existsById(String customerId);

    CustomerResponse findById(String customerId);

    void deleteById(String customerId);

    Page<CustomerResponse> findAllCustomer(int page,int size);

    List<CustomerDocument> search(String keyword);
}
