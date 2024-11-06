package com.chensoul.bookstore.order.application.service;

import com.chensoul.bookstore.order.application.OrderCreateRequest;
import com.chensoul.bookstore.order.application.OrderRequestMapper;
import com.chensoul.bookstore.order.domain.Order;
import com.chensoul.bookstore.order.domain.OrderEvent;
import com.chensoul.bookstore.order.domain.OrderEventMapper;
import com.chensoul.bookstore.order.domain.OrderRepository;
import com.chensoul.bookstore.order.domain.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK");

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public Order createOrder(String userName, OrderCreateRequest request) {
        orderValidator.validate(request);
        Order newOrder = OrderRequestMapper.convertToModel(request);
        newOrder.setUserName(userName);
        this.orderRepository.save(newOrder);
        log.info("Created Order with orderNumber={}", newOrder.getOrderNumber());
        OrderEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(newOrder);
        orderEventService.save(orderCreatedEvent);
        return newOrder;
    }

    public List<Order> findOrders(String userName) {
        return orderRepository.findByUserName(userName);
    }

    public Optional<Order> findUserOrder(String userName, String orderNumber) {
        return orderRepository
                .findByUserNameAndOrderNumber(userName, orderNumber);
    }

    public void processNewOrders() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
        log.info("Found {} new orders to process", orders.size());
        for (Order order : orders) {
            this.process(order);
        }
    }

    private void process(Order order) {
        try {
            if (canBeDelivered(order)) {
                log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
                orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));

            } else {
                log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                orderEventService.save(
                        OrderEventMapper.buildOrderCancelledEvent(order, "Can't deliver to the location"));
            }
        } catch (RuntimeException e) {
            log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
            orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.FAILED);
            orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
        }
    }

    private boolean canBeDelivered(Order order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(
                order.getDeliveryAddress().country().toUpperCase());
    }
}
