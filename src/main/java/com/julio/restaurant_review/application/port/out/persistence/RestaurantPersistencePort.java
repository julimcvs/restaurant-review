package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.Image;
import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.application.dtos.PaginatedRestaurantQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface RestaurantPersistencePort {
    Optional<Restaurant> findById(Long id);

    Page<PaginatedRestaurantQueryDTO> findAllPaginated(
            String name,
            String city,
            String neighborhood,
            Pageable pageable
    );

    Set<Image> findImagesByRestaurantId(Long restaurantId);

    Restaurant save(Restaurant restaurant);
}
