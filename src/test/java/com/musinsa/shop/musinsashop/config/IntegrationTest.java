package com.musinsa.shop.musinsashop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shop.musinsashop.brand.application.BrandService;
import com.musinsa.shop.musinsashop.brand.presentation.BrandController;
import com.musinsa.shop.musinsashop.category.application.CategoryService;
import com.musinsa.shop.musinsashop.category.presentation.CategoryController;
import com.musinsa.shop.musinsashop.product.application.ProductService;
import com.musinsa.shop.musinsashop.product.presentation.ProductController;
import com.musinsa.shop.musinsashop.recommendation.application.RecommendationService;
import com.musinsa.shop.musinsashop.recommendation.presentation.RecommendationController;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@WebMvcTest(
        controllers = {
                BrandController.class,
                CategoryController.class,
                ProductController.class,
                RecommendationController.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)
        }
)
@AutoConfigureRestDocs
@Import(RestDocsConfig.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class IntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected RestDocumentationContextProvider provider;

    @Autowired
    protected BrandController brandController;

    @MockBean
    protected BrandService brandService;

    @Autowired
    protected CategoryController categoryController;

    @MockBean
    protected CategoryService categoryService;

    @Autowired
    protected ProductController productController;

    @MockBean
    protected ProductService productService;

    @Autowired
    protected RecommendationController recommendationController;

    @MockBean
    protected RecommendationService recommendationService;
}
