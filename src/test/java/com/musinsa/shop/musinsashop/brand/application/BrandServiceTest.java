package com.musinsa.shop.musinsashop.brand.application;

import com.musinsa.shop.musinsashop.brand.application.dto.CreateBrandDto;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.domain.repository.BrandRepository;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.BrandRepositoryImpl;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.JpaBrandRepository;
import com.musinsa.shop.musinsashop.config.IsolateDatabase;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@IsolateDatabase
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private JpaBrandRepository jpaBrandRepository;

    private Brand A_브랜드;
    private Brand B_브랜드;

    @BeforeEach
    void setUp(){
        A_브랜드 = new Brand("A");
        B_브랜드 = new Brand("B");
        jpaBrandRepository.save(A_브랜드);
        jpaBrandRepository.save(B_브랜드);
    }

    @Test
    void 브랜드를_조회한다(){
        //when
        final ReadBrandDto brandDto = brandService.selectBrand(1L);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(brandDto).isNotNull();
            softAssertions.assertThat(brandDto.brandName()).isEqualTo(A_브랜드.getBrandName());
        });
    }

    @Test
    void 브랜드_리스트를_조회한다(){
        //when
        final List<ReadBrandDto> brandDto = brandService.selectBrandAll(0, 2);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(brandDto).isNotNull();
            softAssertions.assertThat(brandDto).hasSize(2);
            softAssertions.assertThat(brandDto.get(0).id()).isEqualTo(1L);
            softAssertions.assertThat(brandDto.get(0).brandName()).isEqualTo(A_브랜드.getBrandName());
            softAssertions.assertThat(brandDto.get(1).id()).isEqualTo(2L);
            softAssertions.assertThat(brandDto.get(1).brandName()).isEqualTo(B_브랜드.getBrandName());
        });
    }


    @Test
    void 브랜드를_등록한다(){
        //given
        ReadBrandDto readBrandDto = new ReadBrandDto(3L,"C");
        CreateBrandDto createBrandDto = new CreateBrandDto("C");

        //when
        final ReadBrandDto brandDto = brandService.createBrand(createBrandDto);

        //then
        assertThat(brandDto.brandName()).isEqualTo(readBrandDto.brandName());
    }

    @Test
    void 브랜드를_삭제한다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatCode(() -> brandService.deleteBrand(2L)).doesNotThrowAnyException();
            assertThatThrownBy(() -> brandService.selectBrand(2L))
                    .isInstanceOf(BrandNotFoundException.class)
                    .hasMessage("등록된 브랜드가 없습니다.");
        });
    }

    @Test
    void 삭제할_브랜드가_없으면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> brandService.deleteBrand(111L))
                    .isInstanceOf(BrandNotFoundException.class)
                    .hasMessage("삭제할 브랜드가 없습니다.");
        });
    }

    @Test
    void 브랜드명을_수정한다(){
        //when
        ReadBrandDto readBrandDto = brandService.updateBrand("A", "C");

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(readBrandDto.brandName()).isEqualTo("C");
        });
    }

    @Test
    void 수정할_브랜드가_없으면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> brandService.updateBrand("DDD", "CCC"))
                    .isInstanceOf(BrandNotFoundException.class)
                    .hasMessage("등록된 브랜드가 없습니다.");
        });
    }

    @Test
    void 수정할_브랜드명이_중복이면_예외를_발생시킨다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> brandService.updateBrand("B", "B"))
                    .isInstanceOf(AlreadyBrandException.class)
                    .hasMessage("이미 존재하는 브랜드명 입니다.");
        });
    }
}
