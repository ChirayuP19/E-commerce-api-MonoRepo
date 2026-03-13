package com.chirayu.ecommerce.kafka;

import com.chirayu.ecommerce.email.EmailService;
import com.chirayu.ecommerce.entity.Notification;
import com.chirayu.ecommerce.kafka.order.OrderConfirmation;
import com.chirayu.ecommerce.kafka.payment.PaymentConfirmation;
import com.chirayu.ecommerce.payload.NotificationType;
import com.chirayu.ecommerce.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment-topic:: %s",paymentConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName=paymentConfirmation.customerFirstName()+" "+paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessfullyEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order-topic:: %s",orderConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customer=orderConfirmation.customerResponse().firstname()+" "+orderConfirmation.customerResponse().lastname();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customerResponse().email(),
                customer,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
