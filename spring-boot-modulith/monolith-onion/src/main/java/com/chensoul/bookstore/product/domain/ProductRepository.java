package com.chensoul.bookstore.product.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Optional<Product> findByCode(String code);

    Page<Product> findAll(Pageable pageable);
}
