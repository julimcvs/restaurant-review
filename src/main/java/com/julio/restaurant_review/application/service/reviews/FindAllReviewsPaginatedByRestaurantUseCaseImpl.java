package com.julio.restaurant_review.application.service.reviews;

import com.julio.restaurant_review.application.port.in.usecases.reviews.FindAllReviewsPaginatedByRestaurantUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReviewPersistencePort;
import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllReviewsPaginatedByRestaurantUseCaseImpl implements FindAllReviewsPaginatedByRestaurantUseCase {
    private final ReviewPersistencePort repository;

    public FindAllReviewsPaginatedByRestaurantUseCaseImpl(ReviewPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Page<ReviewDetailDTO> execute(Long restaurantId, Pageable pageable) {
        return this.repository.findReviewsByRestaurantId(restaurantId, pageable);
    }
}
