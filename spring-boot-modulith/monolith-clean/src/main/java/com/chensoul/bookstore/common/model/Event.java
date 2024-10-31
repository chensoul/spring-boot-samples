package com.chensoul.bookstore.common.model;

import com.chensoul.bookstore.order.OrderEventType;
import java.io.Serializable;

public record Event(OrderEventType orderEventType, String key, Object payload) implements Serializable {
}
