package com.chensoul.bookstore.order.web.job;

import com.chensoul.bookstore.order.application.service.OrderEventService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class OrderEventPublishJob {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublishJob.class);

    private final OrderEventService orderEventService;

    OrderEventPublishJob(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    @Scheduled(cron = "${app.publish-order-events-job-cron}")
//    @SchedulerLock(name = "publishOrderEvent")
    public void publishOrderEvents() {
//        LockAssert.assertLocked();
        log.info("Publishing Order Events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
