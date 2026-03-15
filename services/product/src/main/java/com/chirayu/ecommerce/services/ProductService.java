package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.ProductPurchaseRequest;
import com.chirayu.ecommerce.dto.ProductPurchaseResponse;
import com.chirayu.ecommerce.dto.ProductRequest;
import com.chirayu.ecommerce.dto.ProductResponse;
import com.chirayu.ecommerce.entity.ProductDocument;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Long createProduct(@Valid ProductRequest request);

    List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request);

    ProductResponse findById(Long productId);

    List<ProductResponse> findAll();

    String addQuantity(Long productId, double quantityToAdd);

    Page<ProductResponse>findAll(int page,int size);
    List<ProductDocument> search(String keyword);
}
