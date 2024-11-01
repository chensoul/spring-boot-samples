package com.chensoul.bookstore.order.adapter.persistence.jpa;

import com.chensoul.bookstore.order.domain.OrderEvent;
import com.chensoul.bookstore.order.domain.OrderEventRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventRepositoryImpl implements OrderEventRepository {
    private final OrderEventDao orderEventDao;

    @Override
    public void save(OrderEvent orderEvent) {
        orderEventDao.save(OrderEventEntityMapper.convertToEntity(orderEvent));
    }

    @Override
    public List<OrderEvent> findAll(Sort sort) {
        return orderEventDao.findAll().stream().map(OrderEventEntityMapper::convertToModel).collect(Collectors.toList());
    }

    @Override
    public void delete(OrderEvent event) {
        orderEventDao.delete(OrderEventEntityMapper.convertToEntity(event));
    }
}
