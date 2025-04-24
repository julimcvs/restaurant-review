package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.adapter.in.rest.dtos.FilterRestaurantDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.PaginatedRestaurantResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllRestaurantsPaginatedUseCase {
    Page<PaginatedRestaurantResponseDTO> execute(FilterRestaurantDTO filter, Pageable pageable);
}
