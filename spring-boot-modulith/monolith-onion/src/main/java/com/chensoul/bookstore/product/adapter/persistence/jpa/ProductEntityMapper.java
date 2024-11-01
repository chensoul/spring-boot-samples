package com.chensoul.bookstore.product.adapter.persistence.jpa;

import com.chensoul.bookstore.product.domain.Product;

public class ProductEntityMapper {

    public static Product toModel(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
