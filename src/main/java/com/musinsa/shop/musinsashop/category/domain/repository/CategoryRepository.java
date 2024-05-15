package com.musinsa.shop.musinsashop.category.domain.repository;

import com.musinsa.shop.musinsashop.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(final long categoryId);

    Optional<Category> findByCategoryName(final String categoryName);

    Page<Category> findAll(final Pageable pageable);

    Category save(final Category category);

    void bulkSave(final List<Category> categories);

    void delete(final Category category);
}
