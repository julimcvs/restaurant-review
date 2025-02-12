package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RestaurantConfigurationDTO(
        @NotNull
        @Min(0)
        @Max(120)
        Integer vacancyInterval,

        @NotEmpty
        Set<RestaurantScheduleDTO> schedules,

        @NotEmpty
        Set<TableSettingDTO> tableSettings
) {

}
