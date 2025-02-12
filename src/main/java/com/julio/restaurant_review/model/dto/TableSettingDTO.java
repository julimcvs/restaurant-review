package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TableSettingDTO(
        @NotNull
        @Min(1)
        @Max(20)
        Integer tableFor,

        @NotNull
        @Min(1)
        @Max(100)
        Integer vacancyAmount
) {
}
