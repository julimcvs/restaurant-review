package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ReviewDTO(
        @Min(1)
        @Max(99999)
        @NotNull
        Long restaurantId,

        @NotBlank
        @Length(min = 3, max = 100)
        String message,

        @NotNull
        @Min(1)
        @Max(5)
        Integer rating
) {
}
