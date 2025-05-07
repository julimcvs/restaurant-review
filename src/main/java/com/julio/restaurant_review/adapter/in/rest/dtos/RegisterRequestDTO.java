package com.julio.restaurant_review.adapter.in.rest.dtos;

public record RegisterRequestDTO(
    String username,
    String password,
    Long restaurantId
) {
}
