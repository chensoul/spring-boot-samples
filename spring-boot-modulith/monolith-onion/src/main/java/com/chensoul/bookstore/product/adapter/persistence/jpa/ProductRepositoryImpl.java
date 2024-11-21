package com.chensoul.bookstore.product.adapter.persistence.jpa;

import com.chensoul.bookstore.product.domain.Product;
import com.chensoul.bookstore.product.domain.ProductRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;

    @Override
    public Optional<Product> findByCode(String code) {
        return productDao.findByCode(code).map(ProductEntityMapper::toModel);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<ProductEntity> productEntityPage = productDao.findAll(pageable);
        return new PageImpl<>(productEntityPage.stream()
                .map(ProductEntityMapper::toModel).collect(Collectors.toList()),
                pageable, productEntityPage.getTotalElements());
    }
}
