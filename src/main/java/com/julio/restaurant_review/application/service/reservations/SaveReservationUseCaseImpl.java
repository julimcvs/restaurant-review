package com.julio.restaurant_review.application.service.reservations;

import com.julio.restaurant_review.application.port.in.usecases.reservations.CountReservationsByRestaurantUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reservations.SaveReservationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.application.port.out.producers.ReservationEventPublisher;
import com.julio.restaurant_review.application.service.VacancyValidator;
import com.julio.restaurant_review.domain.model.Reservation;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReservationDTO;
import com.julio.restaurant_review.domain.enums.ReservationEventTypeEnum;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SaveReservationUseCaseImpl implements SaveReservationUseCase {
    private final ReservationPersistencePort repository;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final CountReservationsByRestaurantUseCase countReservationsByRestaurantUseCase;
    private final VacancyValidator vacancyValidator;
    private final ReservationEventPublisher eventPublisher;

    public SaveReservationUseCaseImpl(ReservationPersistencePort repository, FindRestaurantByIdUseCase findRestaurantByIdUseCase, CountReservationsByRestaurantUseCase countReservationsByRestaurantUseCase, VacancyValidator vacancyValidator, ReservationEventPublisher eventPublisher) {
        this.repository = repository;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
        this.countReservationsByRestaurantUseCase = countReservationsByRestaurantUseCase;
        this.vacancyValidator = vacancyValidator;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Reservation execute(ReservationDTO input) {
        Reservation reservation = new Reservation();
        var restaurant = this.findRestaurantByIdUseCase.execute(input.restaurantId());
        var reservationCount = this.countReservationsByRestaurantUseCase.execute(input.restaurantId(), input.tableFor(), LocalDate.from(input.scheduledDate()));
        this.vacancyValidator.validate(input.tableFor(), restaurant.getConfiguration(), reservationCount);
        reservation.setScheduledDate(input.scheduledDate());
        reservation.setRestaurant(restaurant);
        reservation.setStatus(ReservationStatusEnum.PENDING);
        reservation.setTableFor(input.tableFor());
        reservation = repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CREATED, reservation.getId());
        return reservation;
    }
}
