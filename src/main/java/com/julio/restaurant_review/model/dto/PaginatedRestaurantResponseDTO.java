package com.julio.restaurant_review.model.dto;

import java.util.Set;

public class PaginatedRestaurantResponseDTO extends PaginatedRestaurantDTO {
    private Set<ImageInfoDTO> images;

    public PaginatedRestaurantResponseDTO(Long id, String name, Double averageRating, Long totalReviews, Set<ImageInfoDTO> images) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.images = images;
    }

    public Set<ImageInfoDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageInfoDTO> images) {
        this.images = images;
    }
}
