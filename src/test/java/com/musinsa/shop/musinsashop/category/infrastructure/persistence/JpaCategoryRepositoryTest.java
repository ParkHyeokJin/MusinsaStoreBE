package com.musinsa.shop.musinsashop.category.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JpaCategoryRepositoryTest {
    @Autowired
    JpaCategoryRepository jpaCategoryRepository;

    @PersistenceContext
    private EntityManager em;

    private Category A_카테고리;

    @BeforeEach
    void setUp(){
        A_카테고리 = new Category("A");

        jpaCategoryRepository.save(A_카테고리);

        em.flush();
        em.clear();
    }

    @Test
    void 카테고리명으로_조회를_한다(){
        //when
        final Optional<Category> byCategoryName = jpaCategoryRepository.findByCategoryName(A_카테고리.getCategoryName());

        //then
        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(byCategoryName.get().getCategoryName()).isEqualTo(A_카테고리.getCategoryName()));
    }

}
