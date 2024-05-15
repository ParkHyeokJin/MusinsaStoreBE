package com.musinsa.shop.musinsashop.product.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Optional<Product> findByProductId(long productId) {
        return jpaProductRepository.findByProductId(productId);
    }

    @Override
    public Optional<Product> findByProductName(String productName) {
        return jpaProductRepository.findByProductName(productName);
    }

    @Override
    public Optional<Product> findByBrandAndProductName(long brandId, String productName) {
        return null;
    }

    @Override
    public Optional<Product> findByCategoryAndProductName(long categoryId, String productName) {
        return null;
    }

    @Override
    public Optional<Product> findByCategoryAndBrandAndProductName(Category category, Brand brand, String productName) {
        return jpaProductRepository.findByCategoryAndBrandAndProductName(category, brand, productName);
    }

    @Override
    public Page<Product> findByBrand(Brand brand, Pageable pageable) {
        return jpaProductRepository.findByBrand(brand, pageable);
    }

    @Override
    public Page<Product> findByCategory(Category category, Pageable pageable) {
        return jpaProductRepository.findByCategory(category, pageable);
    }

    @Override
    public Product create(Product product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        jpaProductRepository.delete(product);
    }

    @Override
    public void saveBulk(List<Product> productList) {
        jpaProductRepository.saveAll(productList);
    }
}
