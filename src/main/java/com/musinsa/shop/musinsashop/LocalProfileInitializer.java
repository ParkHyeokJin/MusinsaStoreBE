package com.musinsa.shop.musinsashop;

import com.musinsa.shop.musinsashop.brand.application.BrandService;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.category.application.CategoryService;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.product.application.ProductService;
import com.musinsa.shop.musinsashop.product.application.dto.CreateProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class LocalProfileInitializer {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;


    @PostConstruct
    void init(){
        initCategory();
        initBrand();
        initProduct();
    }

    private void initCategory() {
        List<String> categoryList = Arrays.asList("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "악세사리");
        categoryService.saveBulkCategory(categoryList);
    }

    private void initBrand() {
        List<String> brandList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");
        brandService.saveBulkBrand(brandList);
    }

    private void initProduct() {
        initABrandProduct();
        initBBrandProduct();
    }

    private void initABrandProduct() {
        ReadBrandDto readBrandDto = brandService.selectBrandByName("A");

        ReadCategoryDto top = categoryService.selectCategoryByName("상의");
        productService.createProduct(CreateProductDto.of("A셔츠", 11200, top.id(), readBrandDto.id()));

        ReadCategoryDto outer = categoryService.selectCategoryByName("아우터");
        productService.createProduct(CreateProductDto.of("A아우터", 5500, outer.id(), readBrandDto.id()));
        
        ReadCategoryDto bottom = categoryService.selectCategoryByName("바지");
        productService.createProduct(CreateProductDto.of("A바지", 4200, bottom.id(), readBrandDto.id()));

        ReadCategoryDto shoes = categoryService.selectCategoryByName("스니커즈");
        productService.createProduct(CreateProductDto.of("A스니커즈", 9000, shoes.id(), readBrandDto.id()));

        ReadCategoryDto bag = categoryService.selectCategoryByName("가방");
        productService.createProduct(CreateProductDto.of("A가방", 2000, bag.id(), readBrandDto.id()));

        ReadCategoryDto hat = categoryService.selectCategoryByName("모자");
        productService.createProduct(CreateProductDto.of("A모자", 1700, hat.id(), readBrandDto.id()));

        ReadCategoryDto shocks = categoryService.selectCategoryByName("양말");
        productService.createProduct(CreateProductDto.of("A양말", 1800, shocks.id(), readBrandDto.id()));

        ReadCategoryDto accessories = categoryService.selectCategoryByName("악세사리");
        productService.createProduct(CreateProductDto.of("A악세사리", 2300, accessories.id(), readBrandDto.id()));
    }

    private void initBBrandProduct() {
        ReadBrandDto readBrandDto = brandService.selectBrandByName("B");

        ReadCategoryDto top = categoryService.selectCategoryByName("상의");
        productService.createProduct(CreateProductDto.of("B셔츠", 10500, top.id(), readBrandDto.id()));

        ReadCategoryDto outer = categoryService.selectCategoryByName("아우터");
        productService.createProduct(CreateProductDto.of("B아우터", 5900, outer.id(), readBrandDto.id()));

        ReadCategoryDto bottom = categoryService.selectCategoryByName("바지");
        productService.createProduct(CreateProductDto.of("B바지", 3800, bottom.id(), readBrandDto.id()));

        ReadCategoryDto shoes = categoryService.selectCategoryByName("스니커즈");
        productService.createProduct(CreateProductDto.of("B스니커즈", 9100, shoes.id(), readBrandDto.id()));

        ReadCategoryDto bag = categoryService.selectCategoryByName("가방");
        productService.createProduct(CreateProductDto.of("B가방", 2100, bag.id(), readBrandDto.id()));

        ReadCategoryDto hat = categoryService.selectCategoryByName("모자");
        productService.createProduct(CreateProductDto.of("B모자", 2000, hat.id(), readBrandDto.id()));

        ReadCategoryDto shocks = categoryService.selectCategoryByName("양말");
        productService.createProduct(CreateProductDto.of("B양말", 2000, shocks.id(), readBrandDto.id()));

        ReadCategoryDto accessories = categoryService.selectCategoryByName("악세사리");
        productService.createProduct(CreateProductDto.of("B악세사리", 2200, accessories.id(), readBrandDto.id()));
    }
}
