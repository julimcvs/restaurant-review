package com.julio.restaurant_review.application.port.out.producers;

import com.julio.restaurant_review.domain.enums.ReservationEventTypeEnum;

public interface ReservationEventPublisher {
    void publishReservationEvent(ReservationEventTypeEnum eventType, Long reservationId);
}
