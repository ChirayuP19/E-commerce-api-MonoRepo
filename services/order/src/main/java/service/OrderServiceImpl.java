package service;

import customer.CustomerClient;
import dto.OrderRequest;
import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import product.ProductClient;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;

    @Override
    public Long createOrder(OrderRequest orderRequest) {
        var customer=this.customerClient.findCustomerFindById(orderRequest.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided customer ID:: "+orderRequest.customerId()));
        return 0L;
    }
}
