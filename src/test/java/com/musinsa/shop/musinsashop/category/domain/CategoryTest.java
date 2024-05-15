package com.musinsa.shop.musinsashop.category.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CategoryTest {
    @Test
    void 카테고리명을_수정할수_있다(){
        //given
        final Category category = new Category("A");

        //when
        category.updateCategoryName("C");

        //then
        Assertions.assertThat(category.categoryName).isEqualTo("C");
    }
}
