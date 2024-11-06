package com.chensoul.bookstore.notification.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventLogRepository extends MongoRepository<EventLogEntity, Long> {
    boolean existsByEventId(String eventId);
}
