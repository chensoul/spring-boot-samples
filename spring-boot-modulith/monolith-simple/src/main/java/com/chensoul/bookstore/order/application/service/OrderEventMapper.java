package com.chensoul.bookstore.order.application.service;

import com.chensoul.bookstore.order.OrderEvent;
import com.chensoul.bookstore.order.OrderEventType;
import com.chensoul.bookstore.order.OrderItem;
import com.chensoul.bookstore.order.domain.OrderEntity;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderEventMapper {

    static OrderEvent buildOrderCreatedEvent(OrderEntity order) {
        return buildOrderEvent(order, OrderEventType.CREATED, null);
    }

    static OrderEvent buildOrderDeliveredEvent(OrderEntity order) {
        return buildOrderEvent(order, OrderEventType.DELIVERED, null);
    }

    static OrderEvent buildOrderCancelledEvent(OrderEntity order, String reason) {
        return buildOrderEvent(order, OrderEventType.CANCELLED, reason);
    }

    static OrderEvent buildOrderErrorEvent(OrderEntity order, String reason) {
        return buildOrderEvent(order, OrderEventType.FAILED, reason);
    }

    private static OrderEvent buildOrderEvent(OrderEntity order, OrderEventType orderEventType, String reason) {
        return new OrderEvent(
                UUID.randomUUID().toString(),
                OrderEventType.CANCELLED,
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now(),
                reason);
    }

    private static Set<OrderItem> getOrderItems(OrderEntity order) {
        return order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
