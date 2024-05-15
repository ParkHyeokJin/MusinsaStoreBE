package com.musinsa.shop.musinsashop.brand.application;

import com.musinsa.shop.musinsashop.brand.application.dto.CreateBrandDto;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public ReadBrandDto createBrand(final CreateBrandDto createBrandDto) {
        brandRepository.selectBrandByName(createBrandDto.brandName())
                .map(existingBrand -> {
                    throw new AlreadyBrandException("이미 등록된 브랜드명 입니다.");
                });

        final Brand saveBrand = brandRepository.saveBrand(createBrandDto.toEntity(createBrandDto.brandName()));
        return ReadBrandDto.from(saveBrand);
    }

    @Transactional
    public void deleteBrand(final long brandId) {
        final Brand deleteBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("삭제할 브랜드가 없습니다."))
                ;
        brandRepository.deleteBrand(deleteBrand);
    }

    @Transactional
    public ReadBrandDto updateBrand(String brandName, String toBrandName) {
        final Brand brand = brandRepository.selectBrandByName(brandName)
                .orElseThrow(() -> new BrandNotFoundException("등록된 브랜드가 없습니다."));

        brandRepository.selectBrandByName(toBrandName)
                .map(existingBrand -> {
                    throw new AlreadyBrandException("이미 존재하는 브랜드명 입니다.");
                });

        brand.updateBrandName(toBrandName);
        brandRepository.updateBrand(brand);
        return ReadBrandDto.from(brand);
    }

    public ReadBrandDto selectBrand(final long brandId) {
        final Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("등록된 브랜드가 없습니다."));
        return ReadBrandDto.from(brand);
    }

    public ReadBrandDto selectBrandByName(final String brandName) {
        final Brand brand = brandRepository.selectBrandByName(brandName)
                .orElseThrow(() -> new BrandNotFoundException("등록된 브랜드가 없습니다."));
        return ReadBrandDto.from(brand);
    }

    public List<ReadBrandDto> selectBrandAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return brandRepository.selectBrandAll(pageable)
                .map(ReadBrandDto::from).toList();
    }

    public void saveBulkBrand(List<String> brandList) {
        List<Brand> saveBrandList = brandList.stream().map(Brand::new).toList();
        brandRepository.saveBulkBrand(saveBrandList);
    }
}
