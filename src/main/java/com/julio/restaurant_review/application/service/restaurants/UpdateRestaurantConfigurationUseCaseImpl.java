package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.UpdateRestaurantConfigurationUseCase;
import com.julio.restaurant_review.application.port.out.persistence.RestaurantPersistencePort;
import com.julio.restaurant_review.domain.model.RestaurantConfiguration;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantConfigurationDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdateRestaurantConfigurationUseCaseImpl implements UpdateRestaurantConfigurationUseCase {
    private final RestaurantPersistencePort repository;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public UpdateRestaurantConfigurationUseCaseImpl(RestaurantPersistencePort repository, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.repository = repository;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    @Override
    public void execute(Long restaurantId, RestaurantConfigurationDTO input) {
        var restaurant = findRestaurantByIdUseCase.execute(restaurantId);
        var configuration = restaurant.getConfiguration();
        var newConfiguration = RestaurantConfiguration.fromDTO(input);
        if (configuration != null) {
            newConfiguration.setId(configuration.getId());
        }
        restaurant.setConfiguration(newConfiguration);
        repository.save(restaurant);
    }
}
