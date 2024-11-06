package com.chensoul.bookstore.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderEventMapper {

    public static OrderEvent buildOrderCreatedEvent(Order order) {
        return buildOrderEvent(order, OrderEventType.CREATED, null);
    }

    public static OrderEvent buildOrderDeliveredEvent(Order order) {
        return buildOrderEvent(order, OrderEventType.DELIVERED, null);
    }

    public static OrderEvent buildOrderCancelledEvent(Order order, String reason) {
        return buildOrderEvent(order, OrderEventType.CANCELLED, reason);
    }

    public static OrderEvent buildOrderErrorEvent(Order order, String reason) {
        return buildOrderEvent(order, OrderEventType.FAILED, reason);
    }

    private static OrderEvent buildOrderEvent(Order order, OrderEventType orderEventType, String reason) {
        return new OrderEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                orderEventType,
                null,
                LocalDateTime.now(),
                reason);
    }

}
