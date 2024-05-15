package com.musinsa.shop.musinsashop.product.presentation;

import com.musinsa.shop.musinsashop.product.application.ProductService;
import com.musinsa.shop.musinsashop.product.application.dto.CreateProductDto;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.product.presentation.dto.request.CreateProductRequest;
import com.musinsa.shop.musinsashop.product.presentation.dto.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/product"))
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest){
        ReadProductDto product = productService.createProduct(CreateProductDto.of(createProductRequest.productName(), createProductRequest.productPrice(), createProductRequest.categoryId(), createProductRequest.brandId()));
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @DeleteMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("정상적으로 삭제 되었습니다.");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> selectProduct(@PathVariable("productId") long productId){
        ReadProductDto readProductDto = productService.findProduct(productId);
        return ResponseEntity.ok(ProductResponse.from(readProductDto));
    }

    @GetMapping("/findByName/{productName}")
    public ResponseEntity<ProductResponse> selectProduct(@PathVariable("productName") String productName){
        ReadProductDto readProductDto = productService.findProductByName(productName);
        return ResponseEntity.ok(ProductResponse.from(readProductDto));
    }

    @GetMapping("/findByBrand/{brandId}")
    public ResponseEntity<List<ProductResponse>> selectProductsByBrand(@PathVariable("brandId") long brandId,
                                                                      @RequestParam(defaultValue = "0")  int pageNumber,
                                                                      @RequestParam(defaultValue = "10")  int pageSize
    ){
        List<ReadProductDto> readProductDtoList = productService.findBrandProductList(brandId, pageNumber, pageSize);
        return ResponseEntity.ok(readProductDtoList.stream().map(ProductResponse::from).toList());
    }

    @GetMapping("/findByCategory/{categoryId}")
    public ResponseEntity<List<ProductResponse>> selectProductsByCategory(@PathVariable("categoryId") long categoryId,
                                                                      @RequestParam(defaultValue = "0")  int pageNumber,
                                                                      @RequestParam(defaultValue = "10")  int pageSize
    ){
        List<ReadProductDto> readProductDtoList = productService.findCategoryProductList(categoryId, pageNumber, pageSize);
        return ResponseEntity.ok(readProductDtoList.stream().map(ProductResponse::from).toList());
    }
}
