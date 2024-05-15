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
        initBrandProduct("A", new int[] {11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300});
        initBrandProduct("B", new int[] {10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200});
        initBrandProduct("C", new int[] {10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100});
        initBrandProduct("D", new int[] {10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000});
        initBrandProduct("E", new int[] {10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100});
        initBrandProduct("F", new int[] {11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900});
        initBrandProduct("G", new int[] {10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000});
        initBrandProduct("H", new int[] {10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000});
        initBrandProduct("I", new int[] {11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400});
    }

    private void initBrandProduct(String brandName, int[] price) {
        int priceIdx = 0;
        ReadBrandDto readBrandDto = brandService.selectBrandByName(brandName);

        ReadCategoryDto top = categoryService.selectCategoryByName("상의");
        productService.createProduct(CreateProductDto.of(brandName+"셔츠", price[priceIdx++], top.id(), readBrandDto.id()));

        ReadCategoryDto outer = categoryService.selectCategoryByName("아우터");
        productService.createProduct(CreateProductDto.of(brandName+"아우터", price[priceIdx++], outer.id(), readBrandDto.id()));

        ReadCategoryDto bottom = categoryService.selectCategoryByName("바지");
        productService.createProduct(CreateProductDto.of(brandName+"바지", price[priceIdx++], bottom.id(), readBrandDto.id()));

        ReadCategoryDto shoes = categoryService.selectCategoryByName("스니커즈");
        productService.createProduct(CreateProductDto.of(brandName+"스니커즈", price[priceIdx++], shoes.id(), readBrandDto.id()));

        ReadCategoryDto bag = categoryService.selectCategoryByName("가방");
        productService.createProduct(CreateProductDto.of(brandName+"가방", price[priceIdx++], bag.id(), readBrandDto.id()));

        ReadCategoryDto hat = categoryService.selectCategoryByName("모자");
        productService.createProduct(CreateProductDto.of(brandName+"모자", price[priceIdx++], hat.id(), readBrandDto.id()));

        ReadCategoryDto shocks = categoryService.selectCategoryByName("양말");
        productService.createProduct(CreateProductDto.of(brandName+"양말", price[priceIdx++], shocks.id(), readBrandDto.id()));

        ReadCategoryDto accessories = categoryService.selectCategoryByName("악세사리");
        productService.createProduct(CreateProductDto.of(brandName+"악세사리", price[priceIdx], accessories.id(), readBrandDto.id()));
    }

}
