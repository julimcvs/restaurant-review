package com.julio.restaurant_review.model.dto;

public record RabbitBindingConfig(
        String queue,
        String exchange,
        String routingKey
) {
}
