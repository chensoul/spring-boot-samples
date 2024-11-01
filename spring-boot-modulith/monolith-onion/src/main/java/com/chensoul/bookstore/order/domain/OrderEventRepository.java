package com.chensoul.bookstore.order.domain;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface OrderEventRepository {

    void save(OrderEvent orderEvent);

    List<OrderEvent> findAll(Sort sort);

    void delete(OrderEvent event);
}
