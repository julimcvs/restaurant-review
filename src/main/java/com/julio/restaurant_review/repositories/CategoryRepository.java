package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
