package com.chensoul.bookstore.order.application;

import com.chensoul.bookstore.order.domain.Address;
import com.chensoul.bookstore.order.domain.Customer;
import com.chensoul.bookstore.order.domain.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

public record OrderCreateRequest(
        @NotEmpty(message = "Items cannot be empty.") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress)
        implements Serializable {}
