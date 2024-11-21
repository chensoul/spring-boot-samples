package com.chensoul.bookstore.product.application.usecase;

import com.chensoul.bookstore.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {
    Page<Product> getProducts(Pageable pageable);

    Optional<Product> getProductByCode(String code);
}
