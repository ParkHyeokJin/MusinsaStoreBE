package com.musinsa.shop.musinsashop.product.application;

import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.infrastructure.persistence.JpaBrandRepository;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.infrastructure.persistence.JpaCategoryRepository;
import com.musinsa.shop.musinsashop.config.IsolateDatabase;
import com.musinsa.shop.musinsashop.product.application.dto.CreateProductDto;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.product.application.exception.AlreadyProductException;
import com.musinsa.shop.musinsashop.product.application.exception.ProductNotFoundException;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.infrastructure.persistence.JpaProductRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@IsolateDatabase
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductServiceTest {
    @Autowired
    private ProductService productService;

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
    void 상품을_생성할수_있다(){
        //given
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 1L, 1L);

        //when
        ReadProductDto product = productService.createProduct(createProductDto);

        //then
        assertThat(product.productName()).isEqualTo(createProductDto.productName());
        assertThat(product.productPrice()).isEqualTo(createProductDto.productPrice());
    }

    @Test
    void 상품을_생성할때_브랜드가_없으면_예외를_발생시킨한다(){
        //given
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 1L, 111L);

        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> productService.createProduct(createProductDto))
                    .isInstanceOf(BrandNotFoundException.class)
                    .hasMessage("해당 하는 브랜드가 없습니다.");
        });
    }

    @Test
    void 상품을_생성할때_카테고리가_없으면_예외를_발생시킨한다(){
        //given
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 111L, 1L);

        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> productService.createProduct(createProductDto))
                    .isInstanceOf(CategoryNotFoundException.class)
                    .hasMessage("해당 하는 카테고리가 없습니다.");
        });
    }

    @Test
    void 중복된_상품을_등록할수_없다(){
        //given
        final CreateProductDto createProductDto = new CreateProductDto("A_상의", 500, 1L, 1L);

        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> productService.createProduct(createProductDto))
                    .isInstanceOf(AlreadyProductException.class)
                    .hasMessage("중복된 상품명을 등록 할 수 없습니다.");
        });
    }

    @Test
    void 상품을_삭제할수_있다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatCode(() -> productService.deleteProduct(1L)).doesNotThrowAnyException();
            assertThatThrownBy(() -> productService.deleteProduct(2222L))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessage("삭제할 상품을 찾을 수 없습니다.");
        });
    }

    @Test
    void 상품아이디로_상품을_조회할수_있다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatCode(() -> productService.findProduct(1L)).doesNotThrowAnyException();
            assertThatThrownBy(() -> productService.findProduct(2222L))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessage("상품을 찾을 수 없습니다.");
        });
    }

    @Test
    void 상품명으로_상품을_조회할수_있다(){
        //when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThatCode(() -> productService.findProductByName("A_상의")).doesNotThrowAnyException();
            assertThatThrownBy(() -> productService.findProductByName("A_상의없음"))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessage("상품을 찾을 수 없습니다.");
        });
    }

    @Test
    void 브랜드에_등록된_상품리스트를_조회할수_있다(){
        //when
        final List<ReadProductDto> brandProductList = productService.findBrandProductList(1L, 0, 2);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(brandProductList).isNotNull();
            softAssertions.assertThat(brandProductList).hasSize(2);
            softAssertions.assertThat(brandProductList.get(0).productName()).isEqualTo(A_브랜드의_A_상의_가장저렴함.getProductName());
            softAssertions.assertThat(brandProductList.get(1).productName()).isEqualTo(A_브랜드의_A_하의_가장저렴함.getProductName());
        });
    }

    @Test
    void 카테고리에_등록된_상품리스트를_조회할수_있다(){
        //when
        final List<ReadProductDto> brandProductList = productService.findCategoryProductList(1L, 0, 2);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(brandProductList).isNotNull();
            softAssertions.assertThat(brandProductList).hasSize(2);
            softAssertions.assertThat(brandProductList.get(0).productName()).isEqualTo(A_브랜드의_A_상의_가장저렴함.getProductName());
            softAssertions.assertThat(brandProductList.get(1).productName()).isEqualTo(B_브랜드의_A_상의.getProductName());
        });
    }
}