package com.chensoul.bookstore.notification.service.event;

import com.chensoul.bookstore.notification.domain.EventLogEntity;
import com.chensoul.bookstore.notification.service.EventLogService;
import com.chensoul.bookstore.notification.service.NotificationService;
import com.chensoul.bookstore.order.OrderCancelledEvent;
import com.chensoul.bookstore.order.OrderCreatedEvent;
import com.chensoul.bookstore.order.OrderDeliveredEvent;
import com.chensoul.bookstore.order.OrderErrorEvent;
import com.chensoul.bookstore.order.OrderEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final EventLogService eventLogService;

    public OrderEventHandler(NotificationService notificationService, EventLogService eventLogService) {
        this.notificationService = notificationService;
        this.eventLogService = eventLogService;
    }

    //    @RabbitListener(queues = "${notification.new-order-queue}")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        if (eventLogService.existsByEventId(orderCreatedEvent.eventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", orderCreatedEvent.eventId());
            return;
        }
        log.info("Received a OrderCreatedEvent with orderNumber:{}: ", orderCreatedEvent.orderNumber());
        notificationService.sendOrderCreatedNotification(orderCreatedEvent);
        var orderEvent = new EventLogEntity(orderCreatedEvent.eventId(), OrderEventType.ORDER_CREATED);
        eventLogService.save(orderEvent);
    }

    //    @RabbitListener(queues = "${notification.delivered-order-queue}")
    public void handle(OrderDeliveredEvent orderDeliveredEvent) {
        if (eventLogService.existsByEventId(orderDeliveredEvent.eventId())) {
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", orderDeliveredEvent.eventId());
            return;
        }
        log.info("Received a OrderDeliveredEvent with orderNumber:{}: ", orderDeliveredEvent.orderNumber());
        notificationService.sendOrderDeliveredNotification(orderDeliveredEvent);
        var orderEvent = new EventLogEntity(orderDeliveredEvent.eventId(), OrderEventType.ORDER_DELIVERED);
        eventLogService.save(orderEvent);
    }

    //    @RabbitListener(queues = "${notification.cancelled-order-queue}")
    public void handle(OrderCancelledEvent orderCancelledEvent) {
        if (eventLogService.existsByEventId(orderCancelledEvent.eventId())) {
            log.warn("Received duplicate OrderCancelledEvent with eventId: {}", orderCancelledEvent.eventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(orderCancelledEvent);
        log.info("Received a OrderCancelledEvent with orderNumber:{}: ", orderCancelledEvent.orderNumber());
        var orderEvent = new EventLogEntity(orderCancelledEvent.eventId(), OrderEventType.ORDER_CANCELLED);
        eventLogService.save(orderEvent);
    }

    //    @RabbitListener(queues = "${notification.error-order-queue}")
    public void handle(OrderErrorEvent orderErrorEvent) {
        if (eventLogService.existsByEventId(orderErrorEvent.eventId())) {
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", orderErrorEvent.eventId());
            return;
        }
        log.info("Received a OrderErrorEvent with orderNumber:{}: ", orderErrorEvent.orderNumber());
        notificationService.sendOrderErrorEventNotification(orderErrorEvent);
        EventLogEntity orderEvent =
                new EventLogEntity(orderErrorEvent.eventId(), OrderEventType.ORDER_PROCESSING_FAILED);
        eventLogService.save(orderEvent);
    }
}
