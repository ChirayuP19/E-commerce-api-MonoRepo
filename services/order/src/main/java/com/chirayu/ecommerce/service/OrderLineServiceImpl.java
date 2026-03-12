package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.orderline.OrderLineRequest;
import com.chirayu.ecommerce.orderline.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import com.chirayu.ecommerce.mapper.OrderLineMapper;

import org.springframework.stereotype.Service;
import com.chirayu.ecommerce.repository.OrderLineRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    @Override
    public void saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        repository.save(order);
    }

    @Override
    public List<OrderLineResponse> findByOrderId(Long orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .toList();
    }
}
