package com.chensoul.bookstore.notification.adapter.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventLogDao extends MongoRepository<EventLogEntity, Long> {
    boolean existsByEventId(String eventId);
}
