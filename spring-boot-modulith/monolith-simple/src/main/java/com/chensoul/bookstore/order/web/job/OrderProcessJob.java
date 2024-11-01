package com.chensoul.bookstore.order.web.job;

import com.chensoul.bookstore.order.application.service.OrderService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class OrderProcessJob {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessJob.class);

    private final OrderService orderService;

    OrderProcessJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${app.new-order-job-cron}")
//    @SchedulerLock(name = "processNewOrder")
    public void processNewOrders() {
//        LockAssert.assertLocked();
        log.info("Processing new orders at {}", Instant.now());
        orderService.processNewOrders();
    }
}
