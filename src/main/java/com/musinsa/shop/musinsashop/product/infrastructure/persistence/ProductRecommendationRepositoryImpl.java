package com.musinsa.shop.musinsashop.product.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRecommendationRepositoryImpl implements ProductRecommendationRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public List<Product> findCategoryMinimumPriceProducts() {
        return jpaProductRepository.findCategoryMinimumPriceProducts();
    }

    @Override
    public Optional<Product> findMinimumPriceProductByCategory(final Category category) {
        return jpaProductRepository.findMinimumPriceProductByCategory(category);
    }

    @Override
    public Optional<Product> findMaximumPriceProductByCategory(final Category category) {
        return jpaProductRepository.findMaximumPriceProductByCategory(category);
    }

    @Override
    public Optional<Brand> findTotalMinimumPriceBrand() {
        return jpaProductRepository.findTotalMinimumPriceBrand();
    }

    @Override
    public List<Product> findByTotalPriceMinimumBrand(final Brand brand) {
        return jpaProductRepository.findByTotalMinimumPriceBrandProducts(brand);
    }
}
