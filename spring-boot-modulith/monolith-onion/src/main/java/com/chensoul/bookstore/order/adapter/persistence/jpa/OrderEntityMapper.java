package com.chensoul.bookstore.order.adapter.persistence.jpa;

import com.chensoul.bookstore.order.domain.Order;
import com.chensoul.bookstore.order.domain.OrderItem;
import com.chensoul.bookstore.order.domain.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderEntityMapper {

    public static OrderEntity convertToEntity(Order order) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderNumber(order.getOrderNumber());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(order.getCustomer());
        newOrder.setDeliveryAddress(order.getDeliveryAddress());
        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (OrderItem item : order.getItems()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }

    public static Order convertToModel(OrderEntity order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new Order(
                null,
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getComments()
        );
    }
}
