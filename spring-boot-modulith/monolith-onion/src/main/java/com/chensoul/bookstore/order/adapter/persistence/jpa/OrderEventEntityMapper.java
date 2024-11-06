package com.chensoul.bookstore.order.adapter.persistence.jpa;

import com.chensoul.bookstore.order.domain.OrderEvent;

public class OrderEventEntityMapper {

    public static OrderEventEntity convertToEntity(OrderEvent orderEvent) {
        OrderEventEntity newOrder = new OrderEventEntity();
        newOrder.setEventId(orderEvent.getEventId());
        newOrder.setOrderNumber(orderEvent.getOrderNumber());
        newOrder.setEventType(orderEvent.getEventType());
        newOrder.setPayload(orderEvent.getPayload());
        newOrder.setCreatedAt(orderEvent.getCreatedAt());
        newOrder.setReason(orderEvent.getReason());
        return newOrder;
    }

    public static OrderEvent convertToModel(OrderEventEntity order) {
        return new OrderEvent(
                order.getEventId(),
                order.getOrderNumber(),
                order.getEventType(),
                order.getPayload(),
                order.getCreatedAt(),
                order.getReason()
        );
    }
}
