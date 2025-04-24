package com.julio.restaurant_review.application.dtos;


public class PaginatedRestaurantQueryDTO extends PaginatedRestaurantDTO {
    public PaginatedRestaurantQueryDTO(Long id, String name, Double averageRating, Long totalReviews) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }
}
