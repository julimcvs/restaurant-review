package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryPersistencePort {
    List<Category> findAll();
    Optional<Category> findById(Long id);
}
