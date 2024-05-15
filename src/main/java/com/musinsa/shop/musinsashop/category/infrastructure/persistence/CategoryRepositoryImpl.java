package com.musinsa.shop.musinsashop.category.infrastructure.persistence;

import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;


    @Override
    public Optional<Category> findById(long categoryId) {
        return jpaCategoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findByCategoryName(String categoryName) {
        return jpaCategoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return jpaCategoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return jpaCategoryRepository.save(category);
    }

    @Override
    public void bulkSave(List<Category> categories) {
        jpaCategoryRepository.saveAll(categories);
    }

    @Override
    public void delete(Category category) {
        jpaCategoryRepository.delete(category);
    }
}
