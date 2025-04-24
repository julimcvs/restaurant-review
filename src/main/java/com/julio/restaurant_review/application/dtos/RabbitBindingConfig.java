package com.julio.restaurant_review.application.dtos;

public record RabbitBindingConfig(
        String queue,
        String exchange,
        String routingKey
) {
}
