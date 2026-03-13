package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.customer.Customer;
import com.chirayu.ecommerce.dto.CustomerRequest;
import com.chirayu.ecommerce.dto.CustomerResponse;
import com.chirayu.ecommerce.exception.CustomerExistException;
import com.chirayu.ecommerce.exception.CustomerNotFoundException;
import com.chirayu.ecommerce.mapper.CustomerMapper;
import com.chirayu.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    @Override
    public String createCustomer(CustomerRequest request) {
        if(customerRepository.existsByEmail(request.email())){
            throw new CustomerExistException(request.email()+" is already exist");
        }
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with the provided ID:: %s", request.id())));

        mergeCustomer(request, customer);
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    @Override
    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    @Override
    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(()-> new CustomerNotFoundException(
                        format("No customer found with the provided ID:: %s", customerId)
                ));
    }

    @Override
    public void deleteById(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private void mergeCustomer(CustomerRequest request, Customer customer) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }
}
