package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.adapter.out.jpa.RestaurantRepository;
import com.julio.restaurant_review.application.port.out.persistence.RestaurantPersistencePort;
import com.julio.restaurant_review.domain.model.Image;
import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.application.dtos.PaginatedRestaurantQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class RestaurantPersistenceAdapter implements RestaurantPersistencePort {
    private final RestaurantRepository repository;

    public RestaurantPersistenceAdapter(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<PaginatedRestaurantQueryDTO> findAllPaginated(String name, String city, String neighborhood, Pageable pageable) {
        return repository.findAllPaginated(name, city, neighborhood, pageable);
    }

    @Override
    public Set<Image> findImagesByRestaurantId(Long restaurantId) {
        return repository.findImagesByRestaurantId(restaurantId);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }
}
