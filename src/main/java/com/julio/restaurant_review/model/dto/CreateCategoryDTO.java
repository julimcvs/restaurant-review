package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDTO(
        @NotBlank String name
) {
}
