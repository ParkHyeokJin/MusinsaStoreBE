package com.musinsa.shop.musinsashop.brand.domain.repository;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    void deleteBrand(final Brand brand);

    Optional<Brand> findById(final long brandId);

    Optional<Brand> selectBrandByName(final String brandName);

    Page<Brand> selectBrandAll(final Pageable pageable);

    Brand saveBrand(final Brand brand);

    void saveBulkBrand(final List<Brand> brandList);

    Brand updateBrand(final Brand brand);
}
