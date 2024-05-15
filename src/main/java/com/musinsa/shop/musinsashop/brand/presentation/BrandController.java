package com.musinsa.shop.musinsashop.brand.presentation;

import com.musinsa.shop.musinsashop.brand.application.BrandService;
import com.musinsa.shop.musinsashop.brand.application.dto.CreateBrandDto;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.presentation.dto.request.CreateBrandRequest;
import com.musinsa.shop.musinsashop.brand.presentation.dto.request.UpdateBrandRequest;
import com.musinsa.shop.musinsashop.brand.presentation.dto.response.BrandResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "브랜드 관리 API")
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody CreateBrandRequest createBrandRequest){
        final ReadBrandDto saveBrand = brandService.createBrand(CreateBrandDto.of(createBrandRequest.brandName()));
        return ResponseEntity.ok(BrandResponse.from(saveBrand));
    }

    @DeleteMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBrand(@PathVariable("brandId") long brandId){
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok().body("정상적으로 삭제 되었습니다.");
    }

    @PutMapping
    @Operation(summary = "브랜드 수정")
    public ResponseEntity<BrandResponse> updateBrand(@Valid @RequestBody UpdateBrandRequest updateBrandRequest){
        final ReadBrandDto brandDto = brandService.updateBrand(updateBrandRequest.oldBrandName(), updateBrandRequest.newBrandName());
        return ResponseEntity.ok(BrandResponse.from(brandDto));
    }

    @GetMapping("/{brandId}")
    @Cacheable(cacheNames = "brand")
    public ResponseEntity<BrandResponse> selectBrand(@PathVariable("brandId") long brandId){
        final ReadBrandDto readBrandDto = brandService.selectBrand(brandId);
        final BrandResponse brandResponse = BrandResponse.from(readBrandDto);
        return ResponseEntity.ok(brandResponse);
    }

    @GetMapping("/findBrandName/{brandName}")
    @Cacheable(cacheNames = "brand")
    public ResponseEntity<BrandResponse> selectBrandByName(@PathVariable("brandName") String brandName){
        final ReadBrandDto readBrandDto = brandService.selectBrandByName(brandName);
        final BrandResponse brandResponse = BrandResponse.from(readBrandDto);
        return ResponseEntity.ok(brandResponse);
    }

    @GetMapping("/brands")
    @Cacheable(cacheNames = "brand")
    public ResponseEntity<List<BrandResponse>> selectBrands(@RequestParam(defaultValue = "0")  int pageNumber,
                                                            @RequestParam(defaultValue = "10")  int pageSize){
        final List<ReadBrandDto> readBrandDtoList = brandService.selectBrandAll(pageNumber, pageSize);
        final List<BrandResponse> brandsResponse =  readBrandDtoList.stream()
                .map(BrandResponse::from)
                .toList();

        return ResponseEntity.ok(brandsResponse);
    }
}
