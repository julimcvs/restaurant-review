package com.julio.restaurant_review.application.port.in.usecases.reviews;

import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllReviewsPaginatedByRestaurantUseCase {
    Page<ReviewDetailDTO> execute(Long restaurantId, Pageable pageable);
}
