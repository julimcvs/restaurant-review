package com.julio.restaurant_review.application.port.in.usecases.restaurants;

import com.julio.restaurant_review.adapter.in.rest.dtos.FindVacanciesRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.GetVacanciesResponseDTO;

public interface GetRestaurantVacanciesUseCase {
    GetVacanciesResponseDTO execute(Long id, FindVacanciesRequestDTO input);
}
