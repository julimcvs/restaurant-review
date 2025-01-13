package com.julio.restaurant_review.model.dto;

public class PaginatedRestaurantDTO {
    protected Long id;
    protected String name;
    protected Double averageRating;
    protected Long totalReviews;

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
