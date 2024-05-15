package com.musinsa.shop.musinsashop.product.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.JpaBrandRepository;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.infrastructure.persistence.JpaCategoryRepository;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRecommendationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductRecommendationRepositoryImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Autowired
    private JpaBrandRepository jpaBrandRepository;

    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;

    private ProductRecommendationRepository productRepository;

    private Brand A_브랜드;
    private Brand B_브랜드;
    private Category category;

    private Product product;
    private Product product2;

    @BeforeEach
    void setUp(){
        A_브랜드 = new Brand("A");
        jpaBrandRepository.save(A_브랜드);

        B_브랜드 = new Brand("B");
        jpaBrandRepository.save(B_브랜드);

        category = new Category("양말");
        jpaCategoryRepository.save(category);

        product = new Product("양말", 500L, A_브랜드, category);
        jpaProductRepository.save(product);

        product2 = new Product("양말", 1500L, A_브랜드, category);
        jpaProductRepository.save(product2);

        product = new Product("양말", 4500L, B_브랜드, category);
        jpaProductRepository.save(product);

        product2 = new Product("양말", 5500L, B_브랜드, category);
        jpaProductRepository.save(product2);

        em.flush();
        em.clear();

        productRepository = new ProductRecommendationRepositoryImpl(jpaProductRepository);
    }

    @Test
    void 카테고리별_최저가격의_상품리스트를_조회할수_있다(){
        //when
        List<Product> categoryMinimumPriceProducts = productRepository.findCategoryMinimumPriceProducts();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(categoryMinimumPriceProducts).hasSize(1);
            softAssertions.assertThat(categoryMinimumPriceProducts.get(0).getProductPrice()).isEqualTo(500);
        });
    }

    @Test
    void 카테고리에_최저가격의_상품을_조회할수_있다(){
        //when
        Optional<Product> minimumPriceProductByCategory = productRepository.findMinimumPriceProductByCategory(category);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(minimumPriceProductByCategory.get().getProductPrice()).isEqualTo(500);
        });
    }

    @Test
    void 카테고리에_최고가격의_상품을_조회할수_있다(){
        //when
        Optional<Product> maximumPriceProductByCategory = productRepository.findMaximumPriceProductByCategory(category);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(maximumPriceProductByCategory.get().getProductPrice()).isEqualTo(5500);
        });
    }

    @Test
    void 전체상품을_가장싸게파는_브랜드를_조회할수_있다(){
        //when
        Optional<Brand> totalMinimumPriceBrand = productRepository.findTotalMinimumPriceBrand();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(totalMinimumPriceBrand.get().getBrandName()).isEqualTo(A_브랜드.getBrandName());
        });
    }

    @Test
    void 전체상품을_가장싸게파는_브랜드의_상품을_조회할수_있다(){
        //when
        List<Product> byTotalPriceMinimumBrand = productRepository.findByTotalPriceMinimumBrand(A_브랜드);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byTotalPriceMinimumBrand).hasSize(2);
            softAssertions.assertThat(byTotalPriceMinimumBrand.get(0).getBrand().getBrandName()).isEqualTo(A_브랜드.getBrandName());
            softAssertions.assertThat(byTotalPriceMinimumBrand.get(1).getBrand().getBrandName()).isEqualTo(A_브랜드.getBrandName());
        });
    }
}