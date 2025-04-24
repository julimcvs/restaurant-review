package com.julio.restaurant_review.application.service.categories;

import com.julio.restaurant_review.application.port.in.usecases.categories.FindAllCategoriesUseCase;
import com.julio.restaurant_review.application.port.out.persistence.CategoryPersistencePort;
import com.julio.restaurant_review.adapter.out.rest.dtos.CategoryListDTO;
import org.springframework.stereotype.Component;

@Component
public class FindAllCategoriesUseCaseImpl implements FindAllCategoriesUseCase {
    private final CategoryPersistencePort repository;

    public FindAllCategoriesUseCaseImpl(CategoryPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public CategoryListDTO[] execute() {
        return repository
                .findAll()
                .stream()
                .map(category -> new CategoryListDTO(category.getId(), category.getName()))
                .toArray(CategoryListDTO[]::new);
    }
}
