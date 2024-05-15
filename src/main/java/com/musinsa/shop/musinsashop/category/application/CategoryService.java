package com.musinsa.shop.musinsashop.category.application;

import com.musinsa.shop.musinsashop.category.application.dto.CreateCategoryDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.application.exception.AlreadyCategoryException;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public ReadCategoryDto saveCategory(final CreateCategoryDto createCategoryDto) {
        categoryRepository.findByCategoryName(createCategoryDto.categoryName())
                .map(existsCategory -> {
                    throw new AlreadyCategoryException("중복된 카테고리 입니다.");
                });

        final Category saveCategory = categoryRepository.save(createCategoryDto.toEntity(createCategoryDto.categoryName()));
        return ReadCategoryDto.from(saveCategory);
    }

    @Transactional
    public void deleteCategory(final long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("삭제할 카테고리가 없습니다."));
        categoryRepository.delete(category);
    }

    @Transactional
    public ReadCategoryDto updateCategory(final String categoryName, final String toCategoryName){
        final Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("등록된 카테고리가 없습니다."));

        categoryRepository.findByCategoryName(toCategoryName)
                .map(extistingCategory -> {
                    throw new AlreadyCategoryException("이미 등록된 카테고리 명이 있습니다.");
                });

        category.updateCategoryName(toCategoryName);
        categoryRepository.save(category);
        return ReadCategoryDto.from(category);
    }

    public List<ReadCategoryDto> selectCategories(final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAll(pageable)
                .stream().map(ReadCategoryDto::from).toList();
    }

    public ReadCategoryDto selectCategory(final long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("등록된 카테고리가 없습니다."));
        return ReadCategoryDto.from(category);
    }

    public ReadCategoryDto selectCategoryByName(final String categoryName) {
        final Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("등록된 카테고리가 없습니다."));
        return ReadCategoryDto.from(category);
    }

    @Transactional
    public void saveBulkCategory(final List<String> categoryList) {
        final List<Category> saveCategoryList = categoryList.stream()
                .map(Category::new).toList();
        categoryRepository.bulkSave(saveCategoryList);
    }
}
