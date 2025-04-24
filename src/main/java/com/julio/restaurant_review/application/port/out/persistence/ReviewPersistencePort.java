package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.Review;
import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewPersistencePort {
    Page<ReviewDetailDTO> findReviewsByRestaurantId(Long restaurantId, Pageable pageable);
    Review save(Review review);
}
