package com.chensoul.bookstore.product.application.service;

import com.chensoul.bookstore.product.Product;
import com.chensoul.bookstore.product.domain.ProductEntity;

class ProductMapper {

    static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
