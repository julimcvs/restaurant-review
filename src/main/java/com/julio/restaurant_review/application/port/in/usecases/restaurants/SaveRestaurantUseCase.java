package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;

public interface SaveRestaurantUseCase {
    Restaurant execute(RestaurantDTO input, MultipartFile[] images);
}
