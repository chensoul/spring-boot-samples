package com.chensoul.bookstore.order.application;

import com.chensoul.bookstore.order.domain.Order;
import com.chensoul.bookstore.order.domain.OrderItem;
import com.chensoul.bookstore.order.domain.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrderRequestMapper {

    public static Order convertToModel(OrderCreateRequest request) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItem> orderItems = new HashSet<>();
        newOrder.setItems(orderItems);
        return newOrder;
    }
}
