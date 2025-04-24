package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.domain.model.Restaurant;

public interface FindRestaurantByIdUseCase {
    Restaurant execute(Long id);
}
