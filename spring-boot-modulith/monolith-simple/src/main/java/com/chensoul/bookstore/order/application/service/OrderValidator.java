package com.chensoul.bookstore.order.application.service;

import com.chensoul.bookstore.order.OrderCreateRequest;
import com.chensoul.bookstore.order.OrderItem;
import com.chensoul.bookstore.order.application.InvalidOrderException;
import com.chensoul.bookstore.product.Product;
import com.chensoul.bookstore.product.application.ProductNotFoundException;
import com.chensoul.bookstore.product.application.service.ProductService;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductService productService;

    OrderValidator(ProductService productService) {
        this.productService = productService;
    }

    void validate(OrderCreateRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = productService.getProductByCode(item.code())
                    .orElseThrow(() -> ProductNotFoundException.forCode(item.code()));
            if (item.price().compareTo(product.price())!=0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.price(),
                        item.price());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
