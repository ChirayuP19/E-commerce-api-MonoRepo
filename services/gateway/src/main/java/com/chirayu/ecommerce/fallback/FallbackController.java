package com.chirayu.ecommerce.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    private static final String STATUS="status";
    private static final String ERROR="error";
    private static final String MESSAGE="message";

    @RequestMapping("/customer")
    public ResponseEntity<Map<String,String>> customerFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        STATUS, ERROR,
                        MESSAGE, "Customer Service is currently unavailable. Please try again later."
                ));
    }

    @RequestMapping("/order")
    public ResponseEntity<Map<String, String>> orderFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        STATUS, ERROR,
                        MESSAGE, "Order Service is currently unavailable. Please try again later."
                ));
    }

    @RequestMapping("/product")
    public ResponseEntity<Map<String, String>> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        STATUS, ERROR,
                        MESSAGE, "Product Service is currently unavailable. Please try again later."
                ));
    }

    @RequestMapping("/payment")
    public ResponseEntity<Map<String, String>> paymentFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        STATUS, ERROR,
                        MESSAGE, "Payment Service is currently unavailable. Please try again later."
                ));
    }

}
