package com.chirayu.ecommerce.controller;

import com.chirayu.ecommerce.dto.ProductPurchaseRequest;
import com.chirayu.ecommerce.dto.ProductPurchaseResponse;
import com.chirayu.ecommerce.dto.ProductRequest;
import com.chirayu.ecommerce.dto.ProductResponse;
import com.chirayu.ecommerce.entity.ProductDocument;
import com.chirayu.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createdProduct(
            @RequestBody @Valid ProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(
            @RequestBody List<ProductPurchaseRequest> request) {
        return new ResponseEntity<>(productService.purchaseProduct(request), HttpStatus.OK);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("product-id") Long productId) {
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size){
        return new ResponseEntity<>(productService.findAll(page,size),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDocument>> search(
            @RequestParam String keyword){
        return new ResponseEntity<>(productService.search(keyword),HttpStatus.OK);
    }


    @PatchMapping("/{product-id}/{quantity}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable("product-id") Long productId,
                                                       @PathVariable("quantity") double quantityToAdd){
        return new ResponseEntity<>(productService.addQuantity(productId,quantityToAdd),HttpStatus.OK);
    }
}
