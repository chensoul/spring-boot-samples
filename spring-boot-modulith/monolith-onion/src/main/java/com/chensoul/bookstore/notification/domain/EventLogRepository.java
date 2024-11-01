package com.chensoul.bookstore.notification.domain;

import com.chensoul.bookstore.notification.adapter.persistence.mongodb.EventLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventLogRepository {
    boolean existsByEventId(String eventId);

    void save(EventLog eventLog);
}
