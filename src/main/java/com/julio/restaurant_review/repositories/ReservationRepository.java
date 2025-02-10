package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
