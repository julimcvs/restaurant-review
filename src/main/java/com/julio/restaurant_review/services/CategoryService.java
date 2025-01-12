package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.model.dto.CategoryListDTO;
import com.julio.restaurant_review.model.entity.Category;
import com.julio.restaurant_review.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryListDTO[] findAll() {
        return repository
                .findAll()
                .stream()
                .map(category -> new CategoryListDTO(category.getId(), category.getName()))
                .toArray(CategoryListDTO[]::new);
    }

    public Category findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
