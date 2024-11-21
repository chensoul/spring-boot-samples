package com.chensoul.bookstore.product.application.service;

import com.chensoul.bookstore.product.application.usecase.ProductQueryService;
import com.chensoul.bookstore.product.domain.Product;
import com.chensoul.bookstore.product.domain.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService implements ProductQueryService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }
}
