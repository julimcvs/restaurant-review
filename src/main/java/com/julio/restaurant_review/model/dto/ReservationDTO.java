package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationDTO(
        @NotNull @Min(0) @Max(99999) Long restaurantId,
        @NotNull @Min(0) @Max(100) Integer tableFor,
        @NotNull LocalDateTime scheduledDate
) {
}

