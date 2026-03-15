package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.customer.Customer;
import com.chirayu.ecommerce.customer.CustomerDocument;
import com.chirayu.ecommerce.dto.CustomerRequest;
import com.chirayu.ecommerce.dto.CustomerResponse;
import com.chirayu.ecommerce.exception.CustomerExistException;
import com.chirayu.ecommerce.exception.CustomerNotFoundException;
import com.chirayu.ecommerce.mapper.CustomerMapper;
import com.chirayu.ecommerce.repository.CustomerRepository;
import com.chirayu.ecommerce.repository.CustomerSearchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerSearchRepository searchRepository;
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

    @Override
    public Page<CustomerResponse> findAllCustomer(int page, int size) {
        var pageable= PageRequest.of(page,size, Sort.by("id").ascending());
        return customerRepository.findAll(pageable)
                .map(mapper::fromCustomer);
    }

    @Override
    public List<CustomerDocument> search(String keyword) {
        return searchRepository
                .findByFirstnameContainingOrLastnameContainingOrEmailContaining(
                        keyword, keyword, keyword);
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

    @PostConstruct
    @Async
    public void syncToElasticsearch() {
        int page = 0;
        int size = 500;
        Page<Customer> customerPage;

        do {
            customerPage = customerRepository.findAll(PageRequest.of(page, size));

            List<CustomerDocument> documents = customerPage.getContent()
                    .stream()
                    .map(customer -> CustomerDocument.builder()
                            .id(customer.getId())
                            .firstname(customer.getFirstname())
                            .lastname(customer.getLastname())
                            .email(customer.getEmail())
                            .build())
                    .toList();

            searchRepository.saveAll(documents);
            page++;

        } while (customerPage.hasNext());
    }
}
