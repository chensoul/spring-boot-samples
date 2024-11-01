package com.chensoul.bookstore.notification.application.service;

import com.chensoul.bookstore.notification.domain.EventLogEntity;
import com.chensoul.bookstore.notification.domain.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventLogService {
    private final EventLogRepository eventLogRepository;

    public boolean existsByEventId(String eventId) {
        return eventLogRepository.existsByEventId(eventId);
    }

    public void save(EventLogEntity orderEvent) {
        eventLogRepository.save(orderEvent);
    }
}
