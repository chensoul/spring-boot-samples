package com.chensoul.bookstore.order.application.message;

import com.chensoul.bookstore.config.ApplicationProperties;
import com.chensoul.bookstore.order.OrderEvent;
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

    public void publish(OrderEvent event) {
        this.send(properties.orderQueue(), event);
    }

    private void send(String queue, OrderEvent payload) {
        publisher.publishEvent(payload);
    }
}
