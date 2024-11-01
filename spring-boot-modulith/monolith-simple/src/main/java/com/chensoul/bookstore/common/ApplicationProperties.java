package com.chensoul.bookstore.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
        String orderQueue,
        String supportEmail) {
}
