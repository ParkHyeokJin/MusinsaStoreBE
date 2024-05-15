package com.musinsa.shop.musinsashop.product.domain.repository;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadCategoriesMinimumPriceProductsDto;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadTotalMinimumPriceBrandDto;

import java.util.List;
import java.util.Optional;

public interface ProductRecommendationRepository {
    List<Product> findCategoryMinimumPriceProducts();

    Optional<Product> findMinimumPriceProductByCategory(final Category category);

    Optional<Product> findMaximumPriceProductByCategory(final Category category);

    Optional<Brand> findTotalMinimumPriceBrand();

    List<Product> findByTotalPriceMinimumBrand(final Brand brand);
}
