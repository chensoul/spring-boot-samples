package com.chensoul.bookstore.order.application.usercase;

import com.chensoul.bookstore.order.application.OrderEventMessage;

public interface OrderEventPublisher {
    void publish(OrderEventMessage orderEventMessage);
}
