package com.musinsa.shop.musinsashop.category.infrastructure.persistence;

import com.musinsa.shop.musinsashop.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(final String categoryName);
}
