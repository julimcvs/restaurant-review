package com.julio.restaurant_review.adapter.out.jpa;

import com.julio.restaurant_review.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
