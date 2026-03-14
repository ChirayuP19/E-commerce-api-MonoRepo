package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.customer.CustomerServiceClient;
import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.dto.PurchaseRequest;
import com.chirayu.ecommerce.kafka.OrderConfirmation;
import com.chirayu.ecommerce.kafka.OrderProducer;
import com.chirayu.ecommerce.payment.PaymentRequest;
import com.chirayu.ecommerce.payment.PaymentServiceClient;
import com.chirayu.ecommerce.product.ProductClientImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.chirayu.ecommerce.mapper.OrderMapper;
import com.chirayu.ecommerce.orderline.OrderLineRequest;
import org.springframework.stereotype.Service;
import com.chirayu.ecommerce.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerServiceClient customerServiceClient;
    private final ProductClientImpl productClient;
    private final PaymentServiceClient paymentServiceClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderRequest orderRequest) {
        var customer = customerServiceClient.findCustomerById(orderRequest.customerId());
        var purchaseProduct = productClient.purchaseProduct(orderRequest.products());
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

        paymentServiceClient.requestPayment(new PaymentRequest(
                null,
                totalAmount,
                orderRequest.paymentMethod(),
                order.getId(),
                reference,
                customer
        ));

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
