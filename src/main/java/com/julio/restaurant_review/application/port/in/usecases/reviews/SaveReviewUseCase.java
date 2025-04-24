package com.julio.restaurant_review.application.port.in.usecases.reviews;

import com.julio.restaurant_review.domain.model.Review;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReviewDTO;

public interface SaveReviewUseCase {
    Review execute(ReviewDTO input);
}
