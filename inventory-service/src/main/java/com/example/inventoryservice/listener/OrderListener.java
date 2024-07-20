package com.example.inventoryservice.listener;

import com.example.common.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderListener {

    @KafkaListener(topics = "order-placed", groupId = "inventory-group")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Received Order Placed Event: {}", event);

        // Simulate inventory reservation
        log.info("Checking inventory for product: {}", event.getProductId());
        log.info("Reserving quantity: {}", event.getQuantity());

        try {
            Thread.sleep(500); // Simulate processing delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Inventory reserved successfully for Order ID: {}", event.getOrderId());
    }
}
