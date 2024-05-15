package com.musinsa.shop.musinsashop.product.application;

import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.domain.repository.BrandRepository;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.domain.repository.CategoryRepository;
import com.musinsa.shop.musinsashop.product.application.dto.CreateProductDto;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.product.application.exception.AlreadyProductException;
import com.musinsa.shop.musinsashop.product.application.exception.ProductNotFoundException;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ReadProductDto createProduct(final CreateProductDto createProductDto){
        final Brand brand = brandRepository.findById(createProductDto.brandId())
                .orElseThrow(() -> new BrandNotFoundException("해당 하는 브랜드가 없습니다."));

        final Category category = categoryRepository.findById(createProductDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("해당 하는 카테고리가 없습니다."));

        productRepository.findByCategoryAndBrandAndProductName(
                        category, brand, createProductDto.productName())
                .map(existingProduct -> {
                    throw new AlreadyProductException("중복된 상품명을 등록 할 수 없습니다.");
                });

        final Product newProduct = createProductDto
                .toEntity(createProductDto.productName(), createProductDto.productPrice(), category, brand);

        return ReadProductDto.from(productRepository.create(newProduct));
    }

    @Transactional
    public void deleteProduct(final long productId){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("삭제할 상품을 찾을 수 없습니다."));

        productRepository.delete(product);
    }

    public ReadProductDto findProduct(final long productId){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ReadProductDto.from(product);
    }

    public ReadProductDto findProductByName(final String productName){
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ReadProductDto.from(product);
    }

    public List<ReadProductDto> findBrandProductList(final long brandId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("해당 하는 브랜드가 없습니다."));

        return productRepository.findByBrand(brand, pageable)
                .map(ReadProductDto::from).toList();
    }

    public List<ReadProductDto> findCategoryProductList(final long categoryId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("해당 하는 카테고리가 없습니다."));

        return productRepository.findByCategory(category, pageable)
                .map(ReadProductDto::from).toList();
    }
}
