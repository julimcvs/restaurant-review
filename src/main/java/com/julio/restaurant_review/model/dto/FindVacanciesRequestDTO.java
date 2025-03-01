package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FindVacanciesRequestDTO(
        @NotNull @Min(0) @Max(100) Integer tableFor,
        @NotNull LocalDate date
) {
}
