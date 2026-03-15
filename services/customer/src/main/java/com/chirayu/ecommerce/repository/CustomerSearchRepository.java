package com.chirayu.ecommerce.repository;

import com.chirayu.ecommerce.customer.CustomerDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerSearchRepository extends ElasticsearchRepository<CustomerDocument,String> {

    List<CustomerDocument> findByFirstnameContainingOrLastnameContainingOrEmailContaining(
            String firstname, String lastname, String email);
}
