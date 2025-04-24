package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantConfigurationDTO;

public interface GetRestaurantConfigurationUseCase {
    RestaurantConfigurationDTO execute(Long id);
}
