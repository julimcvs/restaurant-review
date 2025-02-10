package com.julio.restaurant_review.producers;

import com.julio.restaurant_review.model.enums.ReservationEventTypeEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationEventPublisher {
    private final RabbitTemplate rabbitTemplate;


    public ReservationEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishReservationEvent(ReservationEventTypeEnum eventType, Long reservationId) {
        String routingKey = "reservation." + eventType.toString();
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", eventType.toString());
        message.put("reservationId", reservationId);
        rabbitTemplate.convertAndSend("reservation.events", routingKey, message);
    }
}
