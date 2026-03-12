package com.chirayu.ecommerce.notification;

import com.chirayu.ecommerce.dto.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;
    private static final String TOPIC ="payment-topic";

    public void sendNotification(PaymentNotificationRequest request){
        log.info("Sending notification with body <{}>",request);
        Message<PaymentNotificationRequest> message= MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC,TOPIC)
                .build();
        kafkaTemplate.send(message);
    }

}
