package com.chirayu.ecommerce.product;

import com.chirayu.ecommerce.dto.PurchaseRequest;
import com.chirayu.ecommerce.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {

    @Value("${application.config.product-url}")
    private String productURl;
    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "product-cb", fallbackMethod = "purchaseProductFallback")
    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requestBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {
                };
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productURl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the product purchase: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

    public List<PurchaseResponse> purchaseProductFallback(List<PurchaseRequest> request, Throwable ex) {
        throw new BusinessException(
                "Cannot create order:: Product Service is currently unavailable. Please try again later.");
    }
}
