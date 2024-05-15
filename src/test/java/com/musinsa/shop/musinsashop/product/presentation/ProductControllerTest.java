package com.musinsa.shop.musinsashop.product.presentation;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.config.IntegrationTest;
import com.musinsa.shop.musinsashop.exception.GlobalExceptionHandler;
import com.musinsa.shop.musinsashop.product.application.dto.CreateProductDto;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.product.application.exception.AlreadyProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends IntegrationTest {
    MockMvc mockMvc;

    private final Category category = new Category("상의");
    private final Brand brand = new Brand("A");

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void 상품을_등록할수_있다() throws Exception {
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 1L, 1L);
        final ReadProductDto readProductDto = new ReadProductDto(1L, "A_양말", 500, ReadCategoryDto.from(category), ReadBrandDto.from(brand));

        given(productService.createProduct(createProductDto)).willReturn(readProductDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductDto)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.productName", is(readProductDto.productName())),
                        jsonPath("$.productPrice", is(createProductDto.productPrice()), Long.class),
                        jsonPath("$.category.categoryName", is(category.getCategoryName())),
                        jsonPath("$.brand.brandName", is(brand.getBrandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명"),
                                        fieldWithPath("productPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("category.id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                        fieldWithPath("category.categoryName").type(JsonFieldType.STRING).description("카테고리 명"),
                                        fieldWithPath("brand.id").type(JsonFieldType.NUMBER).description("브랜드 아이디"),
                                        fieldWithPath("brand.brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 상품등록시_브랜드가없는경우_404_오류를_반환한다() throws Exception {
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 111L, 111L);
        final BrandNotFoundException brandNotFoundException = new BrandNotFoundException("해당 하는 브랜드가 없습니다.");
        given(productService.createProduct(createProductDto)).willThrow(brandNotFoundException);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/product")
                        .content(objectMapper.writeValueAsString(createProductDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message", is(brandNotFoundException.getMessage()))
                );
    }

    @Test
    void 상품등록시_카테고리가없는경우_404_오류를_반환한다() throws Exception {
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 111L, 111L);
        final CategoryNotFoundException categoryNotFoundException = new CategoryNotFoundException("해당 하는 카테고리가 없습니다.");
        given(productService.createProduct(createProductDto)).willThrow(categoryNotFoundException);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/product")
                        .content(objectMapper.writeValueAsString(createProductDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message", is(categoryNotFoundException.getMessage()))
                );
    }

    @Test
    void 상품등록시_중복된_상품명이_있는경우_400_오류를_반환한다() throws Exception {
        final CreateProductDto createProductDto = new CreateProductDto("A_양말", 500, 111L, 111L);
        final AlreadyProductException alreadyProductException = new AlreadyProductException("중복된 상품명을 등록 할 수 없습니다.");
        given(productService.createProduct(createProductDto)).willThrow(alreadyProductException);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/product")
                        .content(objectMapper.writeValueAsString(createProductDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message", is(alreadyProductException.getMessage()))
                );
    }

    @Test
    void 상품을_삭제할수_있다() throws Exception {
        willDoNothing().given(productService).deleteProduct(anyLong());

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/product/{productId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("productId").description("삭제할 상품 ID")
                                )
                        ));
    }

    @Test
    void 상품아이디로_상품을_찾을수_있다() throws Exception {
        //given
        final ReadProductDto readProductDto = new ReadProductDto(1L, "A_양말", 500, ReadCategoryDto.from(category), ReadBrandDto.from(brand));
        given(productService.findProduct(anyLong())).willReturn(readProductDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.productName", is(readProductDto.productName())),
                        jsonPath("$.productPrice", is(readProductDto.productPrice()), Long.class),
                        jsonPath("$.category.categoryName", is(category.getCategoryName())),
                        jsonPath("$.brand.brandName", is(brand.getBrandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명"),
                                        fieldWithPath("productPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("category.id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                        fieldWithPath("category.categoryName").type(JsonFieldType.STRING).description("카테고리 명"),
                                        fieldWithPath("brand.id").type(JsonFieldType.NUMBER).description("브랜드 아이디"),
                                        fieldWithPath("brand.brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 상품명으로_상품을_찾을수_있다() throws Exception {
        //given
        final ReadProductDto readProductDto = new ReadProductDto(1L, "A_양말", 500, ReadCategoryDto.from(category), ReadBrandDto.from(brand));
        given(productService.findProductByName(anyString())).willReturn(readProductDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/product/findByName/A_양말")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.productName", is(readProductDto.productName())),
                        jsonPath("$.productPrice", is(readProductDto.productPrice()), Long.class),
                        jsonPath("$.category.categoryName", is(category.getCategoryName())),
                        jsonPath("$.brand.brandName", is(brand.getBrandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 명"),
                                        fieldWithPath("productPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("category.id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                        fieldWithPath("category.categoryName").type(JsonFieldType.STRING).description("카테고리 명"),
                                        fieldWithPath("brand.id").type(JsonFieldType.NUMBER).description("브랜드 아이디"),
                                        fieldWithPath("brand.brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }
}