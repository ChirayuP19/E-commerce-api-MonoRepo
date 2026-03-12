package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.customer.CustomerClient;
import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.dto.PurchaseRequest;
import com.chirayu.ecommerce.exception.BusinessException;
import com.chirayu.ecommerce.kafka.OrderConfirmation;
import com.chirayu.ecommerce.kafka.OrderProducer;
import com.chirayu.ecommerce.payment.PaymentClient;
import com.chirayu.ecommerce.payment.PaymentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.chirayu.ecommerce.mapper.OrderMapper;
import com.chirayu.ecommerce.orderline.OrderLineRequest;
import org.springframework.stereotype.Service;
import com.chirayu.ecommerce.product.ProductClient;
import com.chirayu.ecommerce.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Override
    public Long createOrder(OrderRequest orderRequest) {
        var customer = customerClient.findCustomerFindById(orderRequest.customerId())
                .orElseThrow(() ->
                        new BusinessException("Cannot create order:: No Customer exists with the provided customer ID:: " + orderRequest.customerId()));
        var purchaseProduct = this.productClient.purchaseProduct(orderRequest.products());
        var reference= UUID.randomUUID().toString();
        var totalAmount= purchaseProduct.stream()
                .map(p->p.price().multiply(BigDecimal.valueOf(p.quantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        var order = orderRepository.save(mapper.toOrder(orderRequest,reference,totalAmount));



        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                totalAmount,
                orderRequest.paymentMethod(),
                order.getId(),
                reference,
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        reference,
                        totalAmount,
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseProduct
                )
        );
        return order.getId();
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    @Override
    public OrderResponse findById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException(String.format("No order found with the provided ID: %d",orderId)));

    }
}
