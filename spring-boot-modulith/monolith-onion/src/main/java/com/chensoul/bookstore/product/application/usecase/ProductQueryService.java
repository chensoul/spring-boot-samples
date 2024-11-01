package com.chensoul.bookstore.product.application.usecase;

import com.chensoul.bookstore.common.PagedResult;
import com.chensoul.bookstore.product.domain.Product;
import java.util.Optional;

public interface ProductQueryService {
    PagedResult<Product> getProducts(int pageNo);

    Optional<Product> getProductByCode(String code);
}
