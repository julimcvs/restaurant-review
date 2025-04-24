package com.julio.restaurant_review.application.port.in.usecases.categories;

import com.julio.restaurant_review.adapter.out.rest.dtos.CategoryListDTO;

public interface FindAllCategoriesUseCase {

    CategoryListDTO[] execute();
}
