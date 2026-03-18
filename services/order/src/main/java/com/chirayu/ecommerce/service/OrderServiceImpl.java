package com.chirayu.ecommerce.service;

import com.chirayu.ecommerce.coupon.CouponServiceClient;
import com.chirayu.ecommerce.customer.CustomerServiceClient;
import com.chirayu.ecommerce.dto.OrderRequest;
import com.chirayu.ecommerce.dto.OrderResponse;
import com.chirayu.ecommerce.dto.OrderStatus;
import com.chirayu.ecommerce.dto.PurchaseRequest;
import com.chirayu.ecommerce.exception.BusinessException;
import com.chirayu.ecommerce.kafka.OrderConfirmation;
import com.chirayu.ecommerce.kafka.OrderProducer;
import com.chirayu.ecommerce.payment.PaymentRequest;
import com.chirayu.ecommerce.payment.PaymentServiceClient;
import com.chirayu.ecommerce.product.ProductClientImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.chirayu.ecommerce.mapper.OrderMapper;
import com.chirayu.ecommerce.orderline.OrderLineRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.chirayu.ecommerce.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerServiceClient customerServiceClient;
    private final ProductClientImpl productClient;
    private final PaymentServiceClient paymentServiceClient;
    private final CouponServiceClient couponServiceClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderRequest orderRequest) {
        var customer = customerServiceClient.findCustomerById(orderRequest.customerId());
        var purchaseProduct = productClient.purchaseProduct(orderRequest.products());
        var reference = UUID.randomUUID().toString();
        var totalAmount = purchaseProduct.stream()
                .map(p -> p.price().multiply(BigDecimal.valueOf(p.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var finalAmount=totalAmount;
        if(orderRequest.couponCode() !=null && !orderRequest.couponCode().isBlank()){
            var couponResponse=couponServiceClient.validateCoupon(
                    orderRequest.couponCode(),
                    totalAmount
            );
            finalAmount=couponResponse.finalAmount();
        }
        var order = orderRepository.save(mapper.toOrder(orderRequest, reference, finalAmount));


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
                finalAmount,
                orderRequest.paymentMethod(),
                order.getId(),
                reference,
                customer
        ));

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        reference,
                        finalAmount,
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseProduct
                )
        );
        return order.getId();
    }

    @Override
    public Page<OrderResponse> findAll(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderRepository.findAll(pageable)
                .map(mapper::fromOrder);
    }

    @Override
    public OrderResponse findById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(format("No order found with the provided ID: %d", orderId)));
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("No order found with the provided ID: %d", orderId)));

        validateStatusTransition(order.getOrderStatus(), status);
        order.setOrderStatus(status);
        return mapper.fromOrder(orderRepository.save(order));
    }

    public void validateStatusTransition(OrderStatus current, OrderStatus next) {
        if (current == null) {
            if (next != OrderStatus.PENDING) {
                throw new BusinessException("Order status must start from PENDING");
            }
            return;
        }
        switch (current) {
            case PENDING -> {
                if (next != OrderStatus.CONFIRMED && next != OrderStatus.CANCELLED)
                    throw new BusinessException(
                            "PENDING order can only move to CONFIRMED or CANCELLED");
            }
            case CONFIRMED -> {
                if (next != OrderStatus.PROCESSING && next != OrderStatus.CANCELLED)
                    throw new BusinessException(
                            "CONFIRMED order can only move to PROCESSING or CANCELLED");

            }
            case PROCESSING -> {
                if (next != OrderStatus.SHIPPED)
                    throw new BusinessException(
                            "PROCESSING order can only move to SHIPPED");
            }
            case SHIPPED -> {
                if (next != OrderStatus.DELIVERED)
                    throw new BusinessException(
                            "SHIPPED order can only move to DELIVERED");
            }
            case DELIVERED -> throw new BusinessException(
                    "Order in " + current + " status cannot be updated");
        }
    }
}
