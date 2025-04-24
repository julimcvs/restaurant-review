package com.julio.restaurant_review.application.port.in.usecases.reservations;

import java.time.LocalDate;

public interface CountReservationsByRestaurantUseCase {
    Integer execute(Long restaurantId, Integer tableFor, LocalDate date);
}
