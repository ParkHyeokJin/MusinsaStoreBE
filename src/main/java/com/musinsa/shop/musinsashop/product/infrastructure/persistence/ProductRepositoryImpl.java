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
    public Optional<Product> findByProductId(final long productId) {
        return jpaProductRepository.findByProductId(productId);
    }

    @Override
    public Optional<Product> findByProductName(final String productName) {
        return jpaProductRepository.findByProductName(productName);
    }

    @Override
    public Optional<Product> findByBrandAndProductName(final long brandId, final String productName) {
        return null;
    }

    @Override
    public Optional<Product> findByCategoryAndProductName(final long categoryId, final String productName) {
        return null;
    }

    @Override
    public Optional<Product> findByCategoryAndBrandAndProductName(final Category category, final Brand brand, final String productName) {
        return jpaProductRepository.findByCategoryAndBrandAndProductName(category, brand, productName);
    }

    @Override
    public Page<Product> findByBrand(final Brand brand, final Pageable pageable) {
        return jpaProductRepository.findByBrand(brand, pageable);
    }

    @Override
    public Page<Product> findByCategory(final Category category, final Pageable pageable) {
        return jpaProductRepository.findByCategory(category, pageable);
    }

    @Override
    public Product create(final Product product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public void delete(final Product product) {
        jpaProductRepository.delete(product);
    }

    @Override
    public void saveBulk(final List<Product> productList) {
        jpaProductRepository.saveAll(productList);
    }
}
