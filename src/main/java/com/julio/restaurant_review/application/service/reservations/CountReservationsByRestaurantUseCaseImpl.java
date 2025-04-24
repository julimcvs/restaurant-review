package com.julio.restaurant_review.application.service.reservations;

import com.julio.restaurant_review.application.port.in.usecases.reservations.CountReservationsByRestaurantUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CountReservationsByRestaurantUseCaseImpl implements CountReservationsByRestaurantUseCase {
     private final ReservationPersistencePort repository;

    public CountReservationsByRestaurantUseCaseImpl(ReservationPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Integer execute(Long restaurantId, Integer tableFor, LocalDate date) {
        return this.repository.countRestaurantReservations(restaurantId, tableFor, date.atStartOfDay(), List.of(ReservationStatusEnum.PENDING, ReservationStatusEnum.CONFIRMED));
    }
}
