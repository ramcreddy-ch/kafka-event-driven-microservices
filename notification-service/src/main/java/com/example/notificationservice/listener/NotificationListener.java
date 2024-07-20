package com.example.notificationservice.listener;

import com.example.common.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationListener {

    @KafkaListener(topics = "order-placed", groupId = "notification-group")
    public void sendNotification(OrderPlacedEvent event) {
        log.info("Received Order Placed Event for Notification: {}", event);

        // Simulate sending email
        log.info("Sending order confirmation email to: {}", event.getCustomerEmail());
        log.info("Subject: Order Confirmation - {}", event.getOrderId());
        log.info("Body: Your order for {} x {} has been placed successfully.",
                event.getQuantity(), event.getProductId());

        try {
            Thread.sleep(200); // Simulate network delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Notification sent successfully for Order ID: {}", event.getOrderId());
    }
}
