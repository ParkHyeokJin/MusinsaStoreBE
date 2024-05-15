package com.musinsa.shop.musinsashop.category.application;

import com.musinsa.shop.musinsashop.brand.application.dto.CreateBrandDto;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.application.dto.CreateCategoryDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.application.exception.AlreadyCategoryException;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.infrastructure.persistence.JpaCategoryRepository;
import com.musinsa.shop.musinsashop.config.IsolateDatabase;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IsolateDatabase
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;

    private Category A_카테고리;
    private Category B_카테고리;

    @BeforeEach
    void setUp(){
        A_카테고리 = new Category("A");
        B_카테고리 = new Category("B");
        jpaCategoryRepository.save(A_카테고리);
        jpaCategoryRepository.save(B_카테고리);
    }

    @Test
    void 카테고리를_조회한다(){
        //when
        final ReadCategoryDto readCategoryDto = categoryService.selectCategory(1L);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(readCategoryDto).isNotNull();
            softAssertions.assertThat(readCategoryDto.categoryName()).isEqualTo(A_카테고리.getCategoryName());
        });
    }

    @Test
    void 카테고리_리스트를_조회한다(){
        //when
        final List<ReadCategoryDto> readCategoryDtoList = categoryService.selectCategories(0, 2);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(readCategoryDtoList).isNotNull();
            softAssertions.assertThat(readCategoryDtoList).hasSize(2);
            softAssertions.assertThat(readCategoryDtoList.get(0).id()).isEqualTo(1L);
            softAssertions.assertThat(readCategoryDtoList.get(0).categoryName()).isEqualTo(A_카테고리.getCategoryName());
            softAssertions.assertThat(readCategoryDtoList.get(1).id()).isEqualTo(2L);
            softAssertions.assertThat(readCategoryDtoList.get(1).categoryName()).isEqualTo(B_카테고리.getCategoryName());
        });
    }


    @Test
    void 카테고리를_등록한다(){
        //given
        ReadCategoryDto readCategoryDto = new ReadCategoryDto(3L,"C");
        CreateCategoryDto createCategoryDto = new CreateCategoryDto("C");

        //when
        final ReadCategoryDto categoryDto = categoryService.saveCategory(createCategoryDto);

        //then
        assertThat(categoryDto.categoryName()).isEqualTo(readCategoryDto.categoryName());
    }

    @Test
    void 카테고리를_삭제한다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatCode(() -> categoryService.deleteCategory(2L)).doesNotThrowAnyException();
            assertThatThrownBy(() -> categoryService.deleteCategory(2L))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessage("삭제할 카테고리가 없습니다.");
        });
    }

    @Test
    void 삭제할_카테고리가_없으면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> categoryService.deleteCategory(111L))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessage("삭제할 카테고리가 없습니다.");
        });
    }

    @Test
    void 카테고리명을_수정한다(){
        //when
        final ReadCategoryDto readCategoryDto = categoryService.updateCategory("A", "C");

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(readCategoryDto.categoryName()).isEqualTo("C");
        });
    }

    @Test
    void 수정할_브랜드가_없으면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> categoryService.updateCategory("DDD", "CCC"))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessage("등록된 카테고리가 없습니다.");
        });
    }

    @Test
    void 수정할_브랜드명이_중복이면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> categoryService.updateCategory("B", "B"))
                    .isInstanceOf(AlreadyCategoryException.class)
                    .hasMessage("이미 등록된 카테고리 명이 있습니다.");
        });
    }
}
