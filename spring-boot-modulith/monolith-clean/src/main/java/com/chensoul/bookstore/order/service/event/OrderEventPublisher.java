package com.chensoul.bookstore.order.service.event;

import com.chensoul.bookstore.ApplicationProperties;
import com.chensoul.bookstore.order.OrderCancelledEvent;
import com.chensoul.bookstore.order.OrderCreatedEvent;
import com.chensoul.bookstore.order.OrderDeliveredEvent;
import com.chensoul.bookstore.order.OrderErrorEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final ApplicationEventPublisher publisher;
    private final ApplicationProperties properties;

    OrderEventPublisher(ApplicationEventPublisher publisher, ApplicationProperties properties) {
        this.publisher = publisher;
        this.properties = properties;
    }

    public void publish(OrderCreatedEvent event) {
        this.send(properties.newOrderQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        this.send(properties.deliveredOrderQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        this.send(properties.cancelledOrderQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        this.send(properties.errorOrderQueue(), event);
    }

    private void send(String routingKey, Object payload) {
//        publisher.convertAndSend(properties.orderEventExchange(), routingKey, payload);
    }
}
