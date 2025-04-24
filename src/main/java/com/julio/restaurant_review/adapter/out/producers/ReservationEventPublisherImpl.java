package com.julio.restaurant_review.adapter.out.producers;

import com.julio.restaurant_review.application.port.out.producers.ReservationEventPublisher;
import com.julio.restaurant_review.domain.enums.ReservationEventTypeEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationEventPublisherImpl implements ReservationEventPublisher {
    private final RabbitTemplate rabbitTemplate;


    public ReservationEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishReservationEvent(ReservationEventTypeEnum eventType, Long reservationId) {
        String routingKey = "reservation." + eventType.toString();
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", eventType.toString());
        message.put("reservationId", reservationId);
        rabbitTemplate.convertAndSend("reservation.events", routingKey, message);
    }
}
