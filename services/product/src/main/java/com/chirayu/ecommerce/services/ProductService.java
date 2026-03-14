package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.ProductPurchaseRequest;
import com.chirayu.ecommerce.dto.ProductPurchaseResponse;
import com.chirayu.ecommerce.dto.ProductRequest;
import com.chirayu.ecommerce.dto.ProductResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    Long createProduct(@Valid ProductRequest request);

    List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request);

    ProductResponse findById(Long productId);

    List<ProductResponse> findAll();

    String addQuantity(Long productId, double quantityToAdd);
}
