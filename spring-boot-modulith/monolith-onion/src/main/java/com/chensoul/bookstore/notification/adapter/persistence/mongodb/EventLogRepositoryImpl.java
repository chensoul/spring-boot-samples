package com.chensoul.bookstore.notification.adapter.persistence.mongodb;

import com.chensoul.bookstore.notification.domain.EventLog;
import com.chensoul.bookstore.notification.domain.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventLogRepositoryImpl implements EventLogRepository {
    private final EventLogDao eventLogDao;

    @Override
    public boolean existsByEventId(String eventId) {
        return eventLogDao.existsByEventId(eventId);
    }

    @Override
    public void save(EventLog eventLog) {
        eventLogDao.save(EventLogEntityMapper.toEntity(eventLog));
    }
}
