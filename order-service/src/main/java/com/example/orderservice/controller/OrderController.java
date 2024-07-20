package com.example.orderservice.controller;

import com.example.common.events.OrderPlacedEvent;
import lombok.RequireArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequireArgsConstructor
@Slf4j
public class OrderController {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @PostMapping
    public String placeOrder(@RequestBody OrderPlacedEvent order) {
        order.setOrderId(UUID.randomUUID().toString());

        log.info("Received order request: {}", order);

        // Publish event to Kafka
        kafkaTemplate.send("order-placed", order.getOrderId(), order);

        log.info("Order placed successfully: {}", order.getOrderId());
        return "Order " + order.getOrderId() + " placed successfully";
    }
}
