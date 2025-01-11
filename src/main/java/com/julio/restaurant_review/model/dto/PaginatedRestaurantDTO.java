package com.julio.restaurant_review.model.dto;

public class PaginatedRestaurantDTO {
    private final Long id;
    private final String name;
    private final Double averageRating;
    private Long totalReviews;

    public PaginatedRestaurantDTO(Long id, String name, Double averageRating, Long totalReviews) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }
}
