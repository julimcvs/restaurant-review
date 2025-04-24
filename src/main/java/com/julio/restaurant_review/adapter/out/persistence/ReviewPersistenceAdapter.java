package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.adapter.out.jpa.ReviewRepository;
import com.julio.restaurant_review.application.port.out.persistence.ReviewPersistencePort;
import com.julio.restaurant_review.domain.model.Review;
import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewPersistenceAdapter implements ReviewPersistencePort {
    private ReviewRepository repository;

    public ReviewPersistenceAdapter(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<ReviewDetailDTO> findReviewsByRestaurantId(Long restaurantId, Pageable pageable) {
        return repository.findReviewsByRestaurantId(restaurantId, pageable);
    }

    @Override
    public Review save(Review review) {
        return repository.save(review);
    }
}
