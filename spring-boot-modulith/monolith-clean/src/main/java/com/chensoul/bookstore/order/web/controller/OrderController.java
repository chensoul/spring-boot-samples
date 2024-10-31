package com.chensoul.bookstore.order.web.controller;

import com.chensoul.bookstore.order.CreateOrderRequest;
import com.chensoul.bookstore.order.CreateOrderResponse;
import com.chensoul.bookstore.order.OrderDTO;
import com.chensoul.bookstore.order.OrderSummary;
import com.chensoul.bookstore.order.service.OrderNotFoundException;
import com.chensoul.bookstore.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder("user", request);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        return orderService.findOrders("user");
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber) {
        log.info("Fetching order by id: {}", orderNumber);
        return orderService
                .findUserOrder("user", orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
