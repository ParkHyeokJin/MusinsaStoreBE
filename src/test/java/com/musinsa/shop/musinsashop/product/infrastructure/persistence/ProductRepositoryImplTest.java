package com.musinsa.shop.musinsashop.product.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.JpaBrandRepository;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.infrastructure.persistence.JpaCategoryRepository;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductRepositoryImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Autowired
    private JpaBrandRepository jpaBrandRepository;

    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;

    private ProductRepository productRepository;

    private Brand brand;
    private Category category;

    private Product product;

    @BeforeEach
    void setUp(){
        brand = new Brand("A");
        jpaBrandRepository.save(brand);

        category = new Category("양말");
        jpaCategoryRepository.save(category);

        product = new Product("양말", 500L, brand, category);

        jpaProductRepository.save(product);

        em.flush();
        em.clear();

        productRepository = new ProductRepositoryImpl(jpaProductRepository);
    }

    @Test
    void 상품아이디로_상품을_조회할수_있다(){
        //when
        Optional<Product> byProductId = productRepository.findByProductId(product.getId());

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byProductId.get().getProductName()).isEqualTo("양말");
            softAssertions.assertThat(byProductId.get().getProductPrice()).isEqualTo(500);
        });
    }

    @Test
    void 상품명으로_상품을_조회할수_있다(){
        //when
        Optional<Product> byProductId = productRepository.findByProductName("양말");

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byProductId.get().getProductName()).isEqualTo("양말");
            softAssertions.assertThat(byProductId.get().getProductPrice()).isEqualTo(500);
        });
    }

    @Test
    void 브랜드에_해당하는_상품을_조회할수_있다(){
        //when
        Pageable pageable = PageRequest.of(0, 2);
        Page<Product> byBrand = productRepository.findByBrand(brand, pageable);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byBrand).hasSize(1);
        });
    }

    @Test
    void 카테고리에_해당하는_상품을_조회할수_있다(){
        //when
        Pageable pageable = PageRequest.of(0, 2);
        Page<Product> byCategory = productRepository.findByCategory(category, pageable);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(byCategory).hasSize(1);
        });
    }
}