package com.chensoul.bookstore.order.adapter.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventDao extends JpaRepository<OrderEventEntity, Long> {
}
