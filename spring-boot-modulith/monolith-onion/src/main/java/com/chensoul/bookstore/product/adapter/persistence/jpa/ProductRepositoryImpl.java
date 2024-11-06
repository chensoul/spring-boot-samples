package com.chensoul.bookstore.product.adapter.persistence.jpa;

import com.chensoul.bookstore.common.PagedResult;
import com.chensoul.bookstore.product.domain.Product;
import com.chensoul.bookstore.product.domain.ProductRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PagedResult<Product> findAll(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0:pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, sort);
        Page<ProductEntity> productsPage = productDao.findAll(pageable);

        return new PagedResult<>(
                productsPage.getContent().stream().map(ProductEntityMapper::toModel).collect(Collectors.toList()),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }
}
