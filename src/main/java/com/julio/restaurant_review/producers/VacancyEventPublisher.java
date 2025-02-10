package com.julio.restaurant_review.producers;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VacancyEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public VacancyEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishVacancyGenerationEvent(Long restaurantId) {
        String exchange = "vacancy.events";
        String routingKey = "vacancy.generate";
        rabbitTemplate.convertAndSend(exchange, routingKey, restaurantId);
    }
}
