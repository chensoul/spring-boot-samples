package com.chensoul.bookstore.order.application.service;

import com.chensoul.bookstore.order.application.OrderEventMessage;
import com.chensoul.bookstore.order.application.usercase.OrderEventPublisher;
import com.chensoul.bookstore.order.domain.Order;
import com.chensoul.bookstore.order.domain.OrderEvent;
import com.chensoul.bookstore.order.domain.OrderEventRepository;
import com.chensoul.bookstore.order.domain.OrderEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final ObjectMapper objectMapper;

    OrderEventService(
            OrderEventRepository orderEventRepository,
            OrderEventPublisher orderEventPublisher,
            ObjectMapper objectMapper) {
        this.orderEventRepository = orderEventRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.objectMapper = objectMapper;
    }

    void save(OrderEvent event) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(OrderEventType.CREATED);
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        this.orderEventRepository.save(orderEvent);
    }

    public void publishOrderEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEvent> events = orderEventRepository.findAll(sort);
        log.info("Found {} OrderEvents to be published", events.size());
        for (OrderEvent event : events) {
            Order order = fromJsonPayload(event.getPayload(), Order.class);
            OrderEventMessage orderEventMessage = new OrderEventMessage(
                    event.getEventId(),
                    order.getCustomer(),
                    event.getOrderNumber(),
                    event.getEventType(),
                    event.getPayload(),
                    event.getCreatedAt(),
                    event.getReason()
            );
            this.publishEvent(orderEventMessage);
            orderEventRepository.delete(event);
        }
    }

    private void publishEvent(OrderEventMessage orderEventMessage) {
        orderEventPublisher.publish(orderEventMessage);
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
