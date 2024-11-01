package com.chensoul.bookstore.notification.domain;

public interface EventLogRepository {
    boolean existsByEventId(String eventId);

    void save(EventLog eventLog);
}
