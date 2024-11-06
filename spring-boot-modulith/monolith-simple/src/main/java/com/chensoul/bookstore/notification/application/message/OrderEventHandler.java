package com.chensoul.bookstore.notification.application.message;

import com.chensoul.bookstore.notification.application.service.EventLogService;
import com.chensoul.bookstore.notification.application.service.NotificationService;
import com.chensoul.bookstore.notification.domain.EventLogEntity;
import com.chensoul.bookstore.order.OrderEvent;
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

    //    @RabbitListener(queues = "${notification.order-queue}")
    public void handle(OrderEvent orderEvent) {
        if (eventLogService.existsByEventId(orderEvent.eventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", orderEvent.eventId());
            return;
        }
        log.info("Received a OrderCreatedEvent with orderNumber:{}: ", orderEvent.orderNumber());

        switch (orderEvent.orderEventType()) {
            case CREATED:
                notificationService.sendOrderCreatedNotification(orderEvent);
            case CANCELLED:
                notificationService.sendOrderCancelledNotification(orderEvent);
            case DELIVERED:
                notificationService.sendOrderDeliveredNotification(orderEvent);
            case FAILED:
                notificationService.sendOrderErrorEventNotification(orderEvent);
        }

        var eventLogEntity = new EventLogEntity(orderEvent.eventId(), OrderEventType.CREATED);
        eventLogService.save(eventLogEntity);
    }
}
