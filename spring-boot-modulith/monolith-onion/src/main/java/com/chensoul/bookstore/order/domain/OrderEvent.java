package com.chensoul.bookstore.order.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEvent {
    private String eventId;
    private String orderNumber;
    private OrderEventType eventType;
    private String payload;
    private LocalDateTime createdAt;
    private String reason;

}
