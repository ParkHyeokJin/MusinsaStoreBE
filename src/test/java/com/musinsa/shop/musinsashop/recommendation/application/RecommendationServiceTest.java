package com.musinsa.shop.musinsashop.recommendation.application;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.JpaBrandRepository;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.infrastructure.persistence.JpaCategoryRepository;
import com.musinsa.shop.musinsashop.config.IsolateDatabase;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.infrastructure.persistence.JpaProductRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@IsolateDatabase
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecommendationServiceTest {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    private JpaBrandRepository jpaBrandRepository;

    private Brand A_브랜드_가장저렴함;
    private Brand B_브랜드_가장비쌈;

    private Product A_브랜드의_A_상의_가장저렴함;
    private Product A_브랜드의_A_하의_가장저렴함;
    private Product B_브랜드의_A_상의;
    private Product B_브랜드의_A_하의;

    private Category 상의;
    private Category 하의;

    @BeforeEach
    void setUp(){
        A_브랜드_가장저렴함 = new Brand("A");
        B_브랜드_가장비쌈 = new Brand("B");
        jpaBrandRepository.save(A_브랜드_가장저렴함);
        jpaBrandRepository.save(B_브랜드_가장비쌈);

        상의 = new Category("상의");
        하의 = new Category("하의");
        jpaCategoryRepository.save(상의);
        jpaCategoryRepository.save(하의);

        A_브랜드의_A_상의_가장저렴함 = new Product("A_상의", 2000, A_브랜드_가장저렴함, 상의);
        A_브랜드의_A_하의_가장저렴함 = new Product("A_하의", 1200, A_브랜드_가장저렴함, 하의);
        jpaProductRepository.save(A_브랜드의_A_상의_가장저렴함);
        jpaProductRepository.save(A_브랜드의_A_하의_가장저렴함);

        B_브랜드의_A_상의 = new Product("B_상의", 2500, B_브랜드_가장비쌈, 상의);
        B_브랜드의_A_하의 = new Product("B_하의", 1300, B_브랜드_가장비쌈, 하의);
        jpaProductRepository.save(B_브랜드의_A_상의);
        jpaProductRepository.save(B_브랜드의_A_하의);
    }

    @Test
    void 카테고리별_최저가격의_리스트정보를_조회한다(){
        //when
        List<Product> categoryMinimumPriceProducts = jpaProductRepository.findCategoryMinimumPriceProducts();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(categoryMinimumPriceProducts).isNotNull();
            softAssertions.assertThat(categoryMinimumPriceProducts).hasSize(2);
            softAssertions.assertThat(categoryMinimumPriceProducts.get(0)).isEqualTo(A_브랜드의_A_상의_가장저렴함);
            softAssertions.assertThat(categoryMinimumPriceProducts.get(1)).isEqualTo(A_브랜드의_A_하의_가장저렴함);
        });
    }

    @Test
    void 모든상품을_구매할경우_최저가로_제공하는_브랜드를_조회한다(){
        //when
        List<Product> byTotalMinimumPriceBrandProducts = jpaProductRepository.findByTotalMinimumPriceBrandProducts(A_브랜드_가장저렴함);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byTotalMinimumPriceBrandProducts).isNotNull();
            softAssertions.assertThat(byTotalMinimumPriceBrandProducts).hasSize(2);
            softAssertions.assertThat(byTotalMinimumPriceBrandProducts.get(0)).isEqualTo(A_브랜드의_A_상의_가장저렴함);
            softAssertions.assertThat(byTotalMinimumPriceBrandProducts.get(1)).isEqualTo(A_브랜드의_A_하의_가장저렴함);
        });
    }

    @Test
    void 카테고리중에서_가장_저렴한_상품을_조회한다(){
        //when
        Optional<Product> minimumPriceProductByCategory = jpaProductRepository.findMinimumPriceProductByCategory(상의);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(minimumPriceProductByCategory).isNotNull();
            softAssertions.assertThat(minimumPriceProductByCategory.get()).isEqualTo(A_브랜드의_A_상의_가장저렴함);
        });
    }

    @Test
    void 카테고리중에서_가장_비싼_상품을_조회한다(){
        //when
        Optional<Product> maximumPriceProductByCategory = jpaProductRepository.findMaximumPriceProductByCategory(상의);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(maximumPriceProductByCategory).isNotNull();
            softAssertions.assertThat(maximumPriceProductByCategory.get()).isEqualTo(B_브랜드의_A_상의);
        });
    }

}