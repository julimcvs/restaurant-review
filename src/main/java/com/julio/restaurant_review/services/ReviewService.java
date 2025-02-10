package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.model.dto.ReviewDTO;
import com.julio.restaurant_review.model.dto.ReviewDetailDTO;
import com.julio.restaurant_review.model.entity.Review;
import com.julio.restaurant_review.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository repository;
    private final RestaurantService restaurantService;

    public ReviewService(ReviewRepository repository,
                         RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }



    public Review save(ReviewDTO input) {
        var review = new Review();
        review.setMessage(input.message());
        review.setRating(input.rating());
        var restaurant = this.restaurantService.findEntityById(input.restaurantId());
        review.setRestaurant(restaurant);
        return repository.save(review);
    }

    public Page<ReviewDetailDTO> findPaginatedByRestaurantId(Long restaurantId, Pageable pageable) {
        return this.repository.findReviewsByRestaurantId(restaurantId, pageable);
    }
}
