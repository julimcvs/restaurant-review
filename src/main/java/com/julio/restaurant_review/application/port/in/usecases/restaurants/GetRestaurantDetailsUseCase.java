package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.adapter.out.rest.dtos.RestaurantDetailsDTO;

public interface GetRestaurantDetailsUseCase {
    RestaurantDetailsDTO execute(Long id);
}
