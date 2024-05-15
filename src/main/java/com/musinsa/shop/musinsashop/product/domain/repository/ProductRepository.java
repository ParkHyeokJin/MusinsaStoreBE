package com.musinsa.shop.musinsashop.product.domain.repository;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByProductId(long productId);
    Optional<Product> findByProductName(String productName);

    Optional<Product> findByBrandAndProductName(long brandId, String productName);

    Optional<Product> findByCategoryAndProductName(long categoryId, String productName);

    Optional<Product> findByCategoryAndBrandAndProductName(Category category, Brand brand, String productName);

    Page<Product> findByBrand(Brand brand, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Product create(Product product);

    void delete(Product product);

    void saveBulk(List<Product> productList);
}