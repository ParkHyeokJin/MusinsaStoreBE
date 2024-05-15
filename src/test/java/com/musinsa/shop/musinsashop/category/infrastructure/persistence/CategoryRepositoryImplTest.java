package com.musinsa.shop.musinsashop.category.infrastructure.persistence;

import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.category.domain.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CategoryRepositoryImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    JpaCategoryRepository jpaCategoryRepository;

    CategoryRepository categoryRepository;

    private Category A_카테고리;

    @BeforeEach
    void setUp(){
        A_카테고리 = new Category("A");

        jpaCategoryRepository.save(A_카테고리);

        em.flush();
        em.clear();

        categoryRepository = new CategoryRepositoryImpl(jpaCategoryRepository);
    }

    @Test
    void 카테고리를_생성할수_있다(){
        //when
        Category category = categoryRepository.save(new Category("B"));

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(category.getCategoryName()).isEqualTo("B");
        });
    }
}
