package com.chirayu.ecommerce.repository;

import com.chirayu.ecommerce.entity.Notification;
import com.chirayu.ecommerce.kafka.payment.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
}
