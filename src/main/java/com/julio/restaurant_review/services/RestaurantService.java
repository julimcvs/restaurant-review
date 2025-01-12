package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.entity.Address;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repository;
    private final CategoryService categoryService;

    public RestaurantService(RestaurantRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public Restaurant findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    public Page<PaginatedRestaurantDTO> findAllPaginated(FilterRestaurantDTO filter, Pageable pageable) {
        return repository.findAllPaginated(
                filter.name(),
                filter.city(),
                filter.neighborhood(),
                pageable
        );
    }

    public Restaurant save(RestaurantDTO input) {
        var restaurant = Restaurant.fromDTO(input);
        if (input.id() != null) {
            restaurant = repository
                    .findById(input.id())
                    .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        var address = Address.fromDTO(input.address());
        restaurant.setAddress(address);
        var category = categoryService.findById(input.categoryId());
        restaurant.setCategory(category);
        return repository.save(restaurant);
    }
}
