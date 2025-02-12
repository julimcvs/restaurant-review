package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.entity.Reservation;
import com.julio.restaurant_review.model.enums.ReservationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Integer countReservationByRestaurantIdAndTableForAndScheduledDateAfterAndStatusIn(Long restaurantId, Integer tableFor, LocalDateTime scheduledDate, List<ReservationStatusEnum> status);
}
