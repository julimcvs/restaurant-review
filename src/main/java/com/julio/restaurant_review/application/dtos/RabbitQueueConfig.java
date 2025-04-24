package com.julio.restaurant_review.application.dtos;

public record RabbitQueueConfig (
        String name,
        String dlx,
        String dlqRoutingKey,
        Integer ttl
) {

}
