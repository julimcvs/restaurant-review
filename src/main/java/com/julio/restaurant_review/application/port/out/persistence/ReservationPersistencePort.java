package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.Reservation;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationPersistencePort {
    Integer countRestaurantReservations(Long restaurantId, Integer tableFor, LocalDateTime scheduledDate, List<ReservationStatusEnum> status);

    Optional<Reservation> findById(Long id);

    Reservation save(Reservation reservation);
}
