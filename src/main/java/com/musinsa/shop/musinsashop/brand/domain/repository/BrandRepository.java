package com.musinsa.shop.musinsashop.brand.domain.repository;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    void deleteBrand(Brand brand);

    Optional<Brand> findById(long brandId);

    Optional<Brand> selectBrandByName(String brandName);

    Page<Brand> selectBrandAll(Pageable pageable);

    Brand saveBrand(Brand brand);

    void saveBulkBrand(List<Brand> brandList);

    Brand updateBrand(Brand brand);
}
