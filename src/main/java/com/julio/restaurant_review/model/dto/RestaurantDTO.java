package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RestaurantDTO(
        @Min(1)
        @Max(99999)
        Long id,

        @NotBlank
        @Length(min = 3, max = 100)
        String name,

        @Length(max = 500)
        String description,

        @NotNull
        @Min(1)
        @Max(99999)
        Long categoryId,

        @NotNull
        AddressDTO address
) {
}
