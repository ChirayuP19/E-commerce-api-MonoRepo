package service;

import dto.OrderRequest;
import jakarta.validation.Valid;

public interface OrderService {
    Long createOrder(@Valid OrderRequest orderRequest);
}
