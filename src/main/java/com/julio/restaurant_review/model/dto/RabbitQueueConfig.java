package com.julio.restaurant_review.model.dto;

public record RabbitQueueConfig (
        String name,
        String dlx,
        String dlqRoutingKey,
        Integer ttl
) {

}
