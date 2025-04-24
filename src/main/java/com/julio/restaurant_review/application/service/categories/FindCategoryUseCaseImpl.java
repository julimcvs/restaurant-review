package com.julio.restaurant_review.application.service.categories;

import com.julio.restaurant_review.application.port.in.usecases.categories.FindCategoryUseCase;
import com.julio.restaurant_review.application.port.out.persistence.CategoryPersistencePort;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import com.julio.restaurant_review.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class FindCategoryUseCaseImpl implements FindCategoryUseCase {
    private final CategoryPersistencePort repository;

    public FindCategoryUseCaseImpl(CategoryPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Category execute(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
