package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.orderline.OrderLineRequest;
import com.chirayu.ecommerce.orderline.OrderLineResponse;

import java.util.List;

public interface OrderLineService {

    void saveOrderLine(OrderLineRequest orderLineRequest);

    List<OrderLineResponse> findByOrderId(Long orderId);
}
