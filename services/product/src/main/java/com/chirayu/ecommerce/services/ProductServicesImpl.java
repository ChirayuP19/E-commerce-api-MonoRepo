package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.ProductPurchaseRequest;
import com.chirayu.ecommerce.dto.ProductPurchaseResponse;
import com.chirayu.ecommerce.dto.ProductRequest;
import com.chirayu.ecommerce.dto.ProductResponse;
import com.chirayu.ecommerce.entity.Product;
import com.chirayu.ecommerce.exception.ProductPurchaseException;
import com.chirayu.ecommerce.mapper.ProductMapper;
import com.chirayu.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServicesImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public Long createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {

        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var storeRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
        var productsToUpdate = new ArrayList<Product>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productPurchaseRequest = storeRequest.get(i);

            if (product.getAvailableQuantity() < productPurchaseRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productPurchaseRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productPurchaseRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productsToUpdate.add(product);
            purchaseProducts.add(mapper.toproductPurchaseResponse(product, productPurchaseRequest.quantity()));
        }
        productRepository.saveAll(productsToUpdate);
        return purchaseProducts;
    }


    @Override
    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID :: " + productId));
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
