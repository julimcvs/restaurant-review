package com.julio.restaurant_review.application.service.reviews;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reviews.SaveReviewUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReviewPersistencePort;
import com.julio.restaurant_review.domain.model.Review;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class SaveReviewUseCaseImpl implements SaveReviewUseCase {
    private final ReviewPersistencePort repository;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public SaveReviewUseCaseImpl(ReviewPersistencePort repository, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.repository = repository;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    @Override
    public Review execute(ReviewDTO input) {
        var review = new Review();
        review.setMessage(input.message());
        review.setRating(input.rating());
        var restaurant = findRestaurantByIdUseCase.execute(input.restaurantId());
        review.setRestaurant(restaurant);
        return repository.save(review);
    }
}
