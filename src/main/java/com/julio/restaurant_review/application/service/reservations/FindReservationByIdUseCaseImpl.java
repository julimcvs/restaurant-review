package com.julio.restaurant_review.application.service.reservations;

import com.julio.restaurant_review.application.port.in.usecases.reservations.FindReservationByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.domain.model.Reservation;
import com.julio.restaurant_review.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class FindReservationByIdUseCaseImpl implements FindReservationByIdUseCase {
    private final ReservationPersistencePort repository;

    public FindReservationByIdUseCaseImpl(ReservationPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Reservation execute(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException("Reservation not found."));
    }
}
