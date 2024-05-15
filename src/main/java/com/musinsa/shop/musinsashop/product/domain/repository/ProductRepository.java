package com.musinsa.shop.musinsashop.product.domain.repository;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByProductId(final long productId);
    Optional<Product> findByProductName(final String productName);

    Optional<Product> findByBrandAndProductName(final long brandId, final String productName);

    Optional<Product> findByCategoryAndProductName(final long categoryId, final String productName);

    Optional<Product> findByCategoryAndBrandAndProductName(final Category category, final Brand brand, final String productName);

    Page<Product> findByBrand(final Brand brand, final Pageable pageable);

    Page<Product> findByCategory(final Category category, final Pageable pageable);

    Product create(final Product product);

    void delete(final Product product);

    void saveBulk(final List<Product> productList);
}