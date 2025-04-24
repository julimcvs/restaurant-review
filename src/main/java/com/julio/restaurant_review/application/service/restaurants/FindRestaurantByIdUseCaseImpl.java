package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.RestaurantPersistencePort;
import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class FindRestaurantByIdUseCaseImpl implements FindRestaurantByIdUseCase {
    private final RestaurantPersistencePort repository;

    public FindRestaurantByIdUseCaseImpl(RestaurantPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant execute(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }
}
