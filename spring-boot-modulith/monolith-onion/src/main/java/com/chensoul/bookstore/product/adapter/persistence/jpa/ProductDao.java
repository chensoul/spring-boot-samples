package com.chensoul.bookstore.product.adapter.persistence.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);
}
