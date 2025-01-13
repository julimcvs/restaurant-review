package com.julio.restaurant_review.model.dto;


public class PaginatedRestaurantQueryDTO extends PaginatedRestaurantDTO {
    public PaginatedRestaurantQueryDTO(Long id, String name, Double averageRating, Long totalReviews) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }
}
