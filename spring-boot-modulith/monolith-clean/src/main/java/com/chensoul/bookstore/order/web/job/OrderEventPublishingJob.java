package com.chensoul.bookstore.order.web.job;

import com.chensoul.bookstore.order.service.OrderEventService;
import java.time.Instant;
//import net.javacrumbs.shedlock.core.LockAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class OrderEventPublishingJob {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublishingJob.class);

    private final OrderEventService orderEventService;

    OrderEventPublishingJob(OrderEventService orderEventService) {
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
