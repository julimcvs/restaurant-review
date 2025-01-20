package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.dto.ReviewDetailDTO;
import com.julio.restaurant_review.model.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
            SELECT new com.julio.restaurant_review.model.dto.ReviewDetailDTO(
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
