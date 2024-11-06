package com.chensoul.bookstore.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

public record OrderCreateRequest(
        @NotEmpty(message = "Items cannot be empty.") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress)
        implements Serializable {}
