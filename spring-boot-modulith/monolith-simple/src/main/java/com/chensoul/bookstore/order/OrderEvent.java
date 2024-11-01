package com.chensoul.bookstore.order;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderEvent(
        String eventId,
        OrderEventType orderEventType,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        LocalDateTime createdAt,
        String reason) {
}
