package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.adapter.out.jpa.CategoryRepository;
import com.julio.restaurant_review.application.port.out.persistence.CategoryPersistencePort;
import com.julio.restaurant_review.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryPersistenceAdapter implements CategoryPersistencePort {
    private CategoryRepository repository;

    public CategoryPersistenceAdapter(CategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }
}
