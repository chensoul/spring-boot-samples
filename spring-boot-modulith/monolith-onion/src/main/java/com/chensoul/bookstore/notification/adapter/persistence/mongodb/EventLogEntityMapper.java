package com.chensoul.bookstore.notification.adapter.persistence.mongodb;

import com.chensoul.bookstore.notification.domain.EventLog;

public class EventLogEntityMapper {
    public static EventLogEntity toEntity(EventLog eventLog) {
        EventLogEntity entity = new EventLogEntity();
        entity.setEventId(eventLog.getEventId());
        entity.setType(eventLog.getType());
        return entity;
    }
}
