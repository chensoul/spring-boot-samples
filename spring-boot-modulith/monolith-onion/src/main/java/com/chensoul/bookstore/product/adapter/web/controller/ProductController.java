package com.chensoul.bookstore.product.adapter.web.controller;

import com.chensoul.bookstore.common.PagedResult;
import com.chensoul.bookstore.product.application.exception.ProductNotFoundException;
import com.chensoul.bookstore.product.application.usecase.ProductQueryService;
import com.chensoul.bookstore.product.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductQueryService productQueryService;

    ProductController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching products for page: {}", pageNo);
        return productQueryService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        log.info("Fetching product for code: {}", code);
        return productQueryService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
