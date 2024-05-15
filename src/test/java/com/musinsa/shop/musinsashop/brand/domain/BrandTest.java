package com.musinsa.shop.musinsashop.brand.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BrandTest {
    @Test
    void 브랜드명을_변경한다(){
        // given
        final Brand brand = new Brand("TEST_BRAND");

        //when
        brand.updateBrandName("UPDATE_BRAND");

        //then
        Assertions.assertThat(brand.brandName).isEqualTo("UPDATE_BRAND");
    }
}
