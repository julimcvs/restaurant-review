package com.julio.restaurant_review.adapter.out.jpa;

import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import com.julio.restaurant_review.domain.model.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
            SELECT new com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO(
                review.id,
                review.message,
                review.rating
            )
            FROM Review review
            INNER JOIN review.restaurant restaurant
            WHERE restaurant.id = :restaurantId
            """)
    Page<ReviewDetailDTO> findReviewsByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);
}
