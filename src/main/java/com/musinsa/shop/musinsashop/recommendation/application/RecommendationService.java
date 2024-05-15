package com.musinsa.shop.musinsashop.recommendation.application;

import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.domain.repository.CategoryRepository;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRecommendationRepository;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadProductsMinimumPriceDto;
import com.musinsa.shop.musinsashop.product.application.exception.ProductNotFoundException;
import com.musinsa.shop.musinsashop.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {
    private final ProductRecommendationRepository productRecommendationRepository;
    private final CategoryRepository categoryRepository;

    public List<ReadProductDto> findCategoryMinimumPriceRecommendation(){
        final List<Product> categoryMinimumPriceProducts = productRecommendationRepository.findCategoryMinimumPriceProducts();
        return categoryMinimumPriceProducts.stream().map(ReadProductDto::from).toList();
    }

    public ReadProductsMinimumPriceDto findMinimumPriceAllCategoriesBrandProducts(){
        final Brand brand = productRecommendationRepository.findTotalMinimumPriceBrand()
                .orElseThrow(() -> new BrandNotFoundException("최저가에 해당 되는 브랜드가 없습니다."));

        final List<Product> brandProducts = productRecommendationRepository.findByTotalPriceMinimumBrand(brand);
        return ReadProductsMinimumPriceDto.of(brand.getBrandName(), brandProducts.stream().map(ReadProductDto::from).toList());
    }

    public ReadProductDto findMinimumPriceProductByCategoryName(final String categoryName){
        final Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new BrandNotFoundException("해당 하는 카테고리가 없습니다."));

        final Product product = productRecommendationRepository.findMinimumPriceProductByCategory(category)
                .orElseThrow(() -> new ProductNotFoundException("최저가 상품을 찾을 수 없습니다."));
        return ReadProductDto.from(product);
    }

    public ReadProductDto findMaximumPriceProductByCategoryName(final String categoryName){
        final Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new BrandNotFoundException("해당 하는 카테고리가 없습니다."));

        final Product product = productRecommendationRepository.findMaximumPriceProductByCategory(category)
                .orElseThrow(() -> new ProductNotFoundException("최고가 상품을 찾을 수 없습니다."));
        return ReadProductDto.from(product);
    }
}
