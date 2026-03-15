package com.chirayu.ecommerce.mapper;

import com.chirayu.ecommerce.dto.PaymentRequest;
import com.chirayu.ecommerce.dto.PaymentResponse;
import com.chirayu.ecommerce.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request){
        return Payment.builder()
                .id(request.id())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getOrderId(),
                null
        );
    }
}
