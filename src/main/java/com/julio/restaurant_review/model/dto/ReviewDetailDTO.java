package com.julio.restaurant_review.model.dto;

public class ReviewDetailDTO {
    private final Long id;
    private final String message;
    private final Integer rating;

    public ReviewDetailDTO(Long id, String message, Integer rating) {
        this.id = id;
        this.message = message;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRating() {
        return rating;
    }
}
