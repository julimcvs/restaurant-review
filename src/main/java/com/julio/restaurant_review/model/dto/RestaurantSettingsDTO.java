package com.julio.restaurant_review.model.dto;

import java.util.Set;

public record RestaurantSettingsDTO(
        Set<String> availableDates,
        Integer[] tableSettings
) {

}
