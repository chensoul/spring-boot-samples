package com.chensoul.bookstore.order.application;

import com.chensoul.bookstore.order.domain.Customer;
import com.chensoul.bookstore.order.domain.OrderEventType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderEventMessage {
    private String eventId;
    private Customer customer;
    private String orderNumber;
    private OrderEventType eventType;
    private String payload;
    private LocalDateTime createdAt;
    private String reason;

    public OrderEventMessage(String eventId, Customer customer, String orderNumber, OrderEventType eventType, String payload, LocalDateTime createdAt, String reason) {
        this.eventId = eventId;
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.eventType = eventType;
        this.payload = payload;
        this.createdAt = createdAt;
        this.reason = reason;
    }
}
