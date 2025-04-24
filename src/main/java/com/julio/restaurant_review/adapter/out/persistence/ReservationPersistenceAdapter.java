package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.domain.model.Reservation;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;
import com.julio.restaurant_review.adapter.out.jpa.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationPersistenceAdapter implements ReservationPersistencePort {
    private ReservationRepository repository;

    public ReservationPersistenceAdapter(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer countRestaurantReservations(Long restaurantId, Integer tableFor, LocalDateTime scheduledDate, List<ReservationStatusEnum> status) {
        return this.repository.countReservationByRestaurantIdAndTableForAndScheduledDateAfterAndStatusIn(restaurantId, tableFor, scheduledDate, status);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }
}
