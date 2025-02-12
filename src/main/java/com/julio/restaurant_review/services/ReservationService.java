package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.model.dto.ReservationDTO;
import com.julio.restaurant_review.model.entity.Reservation;
import com.julio.restaurant_review.model.enums.ReservationEventTypeEnum;
import com.julio.restaurant_review.model.enums.ReservationStatusEnum;
import com.julio.restaurant_review.producers.ReservationEventPublisher;
import com.julio.restaurant_review.repositories.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository repository;
    private final ReservationEventPublisher eventPublisher;
    private final RestaurantService restaurantService;

    public ReservationService(
            ReservationRepository repository,
            ReservationEventPublisher eventPublisher,
            @Lazy RestaurantService restaurantService) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.restaurantService = restaurantService;
    }

    public Reservation findEntityById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException("Reservation not found."));
    }

    public Reservation save(ReservationDTO input) {
        Reservation reservation = new Reservation();
        var restaurant = this.restaurantService.findEntityById(input.restaurantId());
        var reservationCount = this.countReservationsByRestaurant(input.restaurantId(), input.tableFor(), LocalDate.from(input.scheduledDate()));
        this.restaurantService.validateVacancyAvailability(input.tableFor(), restaurant.getConfiguration(), reservationCount);
        reservation.setScheduledDate(input.scheduledDate());
        reservation.setRestaurant(restaurant);
        reservation.setStatus(ReservationStatusEnum.PENDING);
        reservation.setTableFor(input.tableFor());
        reservation = repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CREATED, reservation.getId());
        return reservation;
    }

    public Void confirm(Long id) {
        var reservation = this.findEntityById(id);
        reservation.setStatus(ReservationStatusEnum.CONFIRMED);
        repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CONFIRMED, reservation.getId());
        return null;
    }

    public Void cancel(Long id) {
        var reservation = this.findEntityById(id);
        reservation.setStatus(ReservationStatusEnum.CANCELED);
        repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CANCELED, reservation.getId());
        return null;
    }

    public Integer countReservationsByRestaurant(Long restaurantId, Integer tableFor, LocalDate date) {
        return this.repository.countReservationByRestaurantIdAndTableForAndScheduledDateAfterAndStatusIn(restaurantId, tableFor, date.atStartOfDay(), List.of(ReservationStatusEnum.PENDING, ReservationStatusEnum.CONFIRMED));
    }
}
