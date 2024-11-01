package com.chensoul.bookstore.product.domain;

import com.chensoul.bookstore.common.PagedResult;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByCode(String code);

    PagedResult<Product> findAll(int pageNo);
}
