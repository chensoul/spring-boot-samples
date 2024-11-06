package com.chensoul.bookstore.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void updateOrderStatus(String orderNumber, OrderStatus orderStatus);

    List<Order> findByUserName(String userName);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByUserNameAndOrderNumber(String userName, String orderNumber);

    Order save(Order order);
}
