package com.chensoul.bookstore.order.adapter.message.publisher;

import com.chensoul.bookstore.order.application.OrderEventMessage;
import com.chensoul.bookstore.order.application.usercase.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventPublisherImpl implements OrderEventPublisher {
    private final ApplicationEventPublisher publisher;

    @Value(("${app.orderQueue}"))
    private String orderQueue;

    public void publish(OrderEventMessage orderEventMessage) {
        this.send(orderQueue, orderEventMessage);
    }

    private void send(String queue, OrderEventMessage orderEventMessage) {
        publisher.publishEvent(orderEventMessage);
    }
}
