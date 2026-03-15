package com.chirayu.ecommerce.services;

import com.chirayu.ecommerce.dto.PaymentNotificationRequest;
import com.chirayu.ecommerce.dto.PaymentRequest;
import com.chirayu.ecommerce.dto.PaymentResponse;
import com.chirayu.ecommerce.mapper.PaymentMapper;
import com.chirayu.ecommerce.notification.NotificationProducer;
import com.chirayu.ecommerce.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    @Override
    public Long createPayment(PaymentRequest request) {
        var payment=repository.save(mapper.toPayment(request));
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }

    @Override
    public Page<PaymentResponse> findAll(int page, int size) {
        var pageable= PageRequest.of(page,size, Sort.by("id").ascending());
        return repository.findAll(pageable)
                .map(mapper::toPaymentResponse);
    }

    @Override
    public PaymentResponse findById(Long paymentId) {
        return repository.findById(paymentId)
                .map(mapper::toPaymentResponse)
                .orElseThrow(()-> new EntityNotFoundException(
                "Payment not found with ID: " + paymentId));
    }
}
