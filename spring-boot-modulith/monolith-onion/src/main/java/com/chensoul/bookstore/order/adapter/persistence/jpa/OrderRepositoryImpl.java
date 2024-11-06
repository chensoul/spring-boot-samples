package com.chensoul.bookstore.order.adapter.persistence.jpa;

import com.chensoul.bookstore.order.domain.Order;
import com.chensoul.bookstore.order.domain.OrderRepository;
import com.chensoul.bookstore.order.domain.OrderStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;

    @Override
    public void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        orderDao.updateOrderStatus(orderNumber, orderStatus);
    }

    @Override
    public List<Order> findByUserName(String userName) {
        return orderDao.findByUserName(userName).stream().map(OrderEntityMapper::convertToModel).collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderDao.findByStatus(status).stream().map(OrderEntityMapper::convertToModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderDao.findByOrderNumber(orderNumber).map(OrderEntityMapper::convertToModel);
    }

    @Override
    public Optional<Order> findByUserNameAndOrderNumber(String userName, String orderNumber) {
        return orderDao.findByUserNameAndOrderNumber(userName, orderNumber).map(OrderEntityMapper::convertToModel);
    }

    @Override
    public Order save(Order order) {
        return OrderEntityMapper.convertToModel(orderDao.save(OrderEntityMapper.convertToEntity(order)));
    }
}
