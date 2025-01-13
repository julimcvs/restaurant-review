package com.julio.restaurant_review.model.dto;

public class PaginatedRestaurantResponseDTO extends PaginatedRestaurantDTO {
    private ImageInfoDTO image;

    public PaginatedRestaurantResponseDTO(Long id, String name, Double averageRating, Long totalReviews, ImageInfoDTO image) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.image = image;
    }

    public ImageInfoDTO getImage() {
        return image;
    }

    public void setImage(ImageInfoDTO image) {
        this.image = image;
    }
}
