package com.julio.restaurant_review.application.port.in.usecases.categories;

import com.julio.restaurant_review.domain.model.Category;

public interface FindCategoryUseCase {
    Category execute(Long id);
}
