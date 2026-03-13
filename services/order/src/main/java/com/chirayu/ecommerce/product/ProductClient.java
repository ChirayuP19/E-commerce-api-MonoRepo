package com.chirayu.ecommerce.product;

import com.chirayu.ecommerce.dto.PurchaseRequest;

import java.util.List;

public interface ProductClient {

    List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requestBody);
}
