package com.julio.restaurant_review.application.port.in.usecases.reservations;

import com.julio.restaurant_review.domain.model.Reservation;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReservationDTO;

public interface SaveReservationUseCase {
    Reservation execute(ReservationDTO input);
}
