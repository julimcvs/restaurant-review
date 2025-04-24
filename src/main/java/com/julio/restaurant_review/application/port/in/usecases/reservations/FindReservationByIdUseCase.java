package com.julio.restaurant_review.application.port.in.usecases.reservations;

import com.julio.restaurant_review.domain.model.Reservation;

public interface FindReservationByIdUseCase {
    Reservation execute(Long id);
}
