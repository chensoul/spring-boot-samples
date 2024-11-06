package com.chensoul.bookstore.notification.domain;

import com.chensoul.bookstore.order.domain.OrderEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLog {

    private String eventId;

    private OrderEventType type;
}
