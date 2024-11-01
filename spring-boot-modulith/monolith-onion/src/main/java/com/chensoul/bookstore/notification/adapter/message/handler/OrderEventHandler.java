package com.chensoul.bookstore.notification.adapter.message.handler;

import com.chensoul.bookstore.notification.application.EventLogService;
import com.chensoul.bookstore.notification.application.NotificationService;
import com.chensoul.bookstore.notification.domain.EventLog;
import com.chensoul.bookstore.order.application.OrderEventMessage;
import com.chensoul.bookstore.order.domain.OrderEventType;
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
    public void handle(OrderEventMessage orderEventMessage) {
        if (eventLogService.existsByEventId(orderEventMessage.getEventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", orderEventMessage.getEventId());
            return;
        }
        log.info("Received a OrderCreatedEvent with orderNumber:{}: ", orderEventMessage.getOrderNumber());

        switch (orderEventMessage.getEventType()) {
            case CREATED:
                notificationService.sendOrderCreatedNotification(orderEventMessage);
            case CANCELLED:
                notificationService.sendOrderCancelledNotification(orderEventMessage);
            case DELIVERED:
                notificationService.sendOrderDeliveredNotification(orderEventMessage);
            case FAILED:
                notificationService.sendOrderErrorEventNotification(orderEventMessage);
        }

        var eventLogEntity = new EventLog(orderEventMessage.getEventId(), OrderEventType.CREATED);
        eventLogService.save(eventLogEntity);
    }
}
