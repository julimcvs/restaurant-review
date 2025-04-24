package com.julio.restaurant_review.adapter.out.rest.dtos;

import com.julio.restaurant_review.application.dtos.ImageInfoDTO;
import com.julio.restaurant_review.application.dtos.PaginatedRestaurantDTO;

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
