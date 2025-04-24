package com.julio.restaurant_review.adapter.in.rest.dtos;

import org.hibernate.validator.constraints.Length;

public record FilterRestaurantDTO(
        @Length(max = 100)
        String name,

        @Length(max = 100)
        String city,

        @Length(max = 100)
        String neighborhood
) {
}
