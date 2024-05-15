package com.musinsa.shop.musinsashop.brand.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final JpaBrandRepository jpaBrandRepository;

    @Override
    public void deleteBrand(Brand brand) {
        jpaBrandRepository.delete(brand);
    }

    @Override
    public Optional<Brand> findById(long brandId) {
        return jpaBrandRepository.findById(brandId);
    }

    @Override
    public Optional<Brand> selectBrandByName(String brandName) {
        return jpaBrandRepository.findByBrandName(brandName);
    }

    @Override
    public Page<Brand> selectBrandAll(Pageable pageable) {
        return jpaBrandRepository.findAll(pageable);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return jpaBrandRepository.save(brand);
    }

    @Override
    public void saveBulkBrand(List<Brand> brandList) {
        jpaBrandRepository.saveAll(brandList);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return jpaBrandRepository.save(brand);
    }
}
