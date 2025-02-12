package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record RestaurantScheduleDTO(
        @NotNull
        @Min(1)
        @Max(7)
        DayOfWeek dayOfWeek,

        @NotNull
        LocalTime openingTime,

        @NotNull
        LocalTime closingTime
) {
}
