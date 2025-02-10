package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationDTO(
        @Min(1)
        @Max(99999)
        @NotNull
        Long vacancyId
) {
}
