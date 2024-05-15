package com.musinsa.shop.musinsashop.category.presentation;

import com.musinsa.shop.musinsashop.category.application.CategoryService;
import com.musinsa.shop.musinsashop.category.application.dto.CreateCategoryDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.presentation.dto.request.CreateCategoryRequest;
import com.musinsa.shop.musinsashop.category.presentation.dto.request.UpdateCategoryRequest;
import com.musinsa.shop.musinsashop.category.presentation.dto.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        ReadCategoryDto readCategoryDto = categoryService.saveCategory(CreateCategoryDto.of(createCategoryRequest.categoryName()));
        return ResponseEntity.ok(CategoryResponse.from(readCategoryDto));
    }

    @DeleteMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body("정상적으로 삭제 되었습니다.");
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest){
        ReadCategoryDto readCategoryDto = categoryService.updateCategory(updateCategoryRequest.oldCategoryName(), updateCategoryRequest.newCategoryName());
        return ResponseEntity.ok(CategoryResponse.from(readCategoryDto));
    }

    @GetMapping("/{categoryId}")
    @Cacheable(cacheNames = "category")
    public ResponseEntity<CategoryResponse> selectCategory(@PathVariable("categoryId") long categoryId){
        final ReadCategoryDto readCategoryDto = categoryService.selectCategory(categoryId);
        final CategoryResponse categoryResponse = CategoryResponse.from(readCategoryDto);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/findCategoryName/{categoryName}")
    @Cacheable(cacheNames = "category")
    public ResponseEntity<CategoryResponse> selectCategoryByName(@PathVariable("categoryName") String categoryName){
        final ReadCategoryDto readCategoryDto = categoryService.selectCategoryByName(categoryName);
        final CategoryResponse categoryResponse = CategoryResponse.from(readCategoryDto);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/categories")
    @Cacheable(cacheNames = "category")
    public ResponseEntity<List<CategoryResponse>> selectCategories(@RequestParam(defaultValue = "0")  int pageNumber,
                                                                   @RequestParam(defaultValue = "10")  int pageSize){
        final List<ReadCategoryDto> readCategories = categoryService.selectCategories(pageNumber, pageSize);
        final List<CategoryResponse> categoriesResponse = readCategories.stream()
                .map(CategoryResponse::from)
                .toList();

        return ResponseEntity.ok(categoriesResponse);
    }
}