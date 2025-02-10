package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.model.dto.ReservationDTO;
import com.julio.restaurant_review.model.entity.Reservation;
import com.julio.restaurant_review.model.enums.ReservationEventTypeEnum;
import com.julio.restaurant_review.model.enums.ReservationStatusEnum;
import com.julio.restaurant_review.model.enums.VacancyStatusEnum;
import com.julio.restaurant_review.producers.ReservationEventPublisher;
import com.julio.restaurant_review.repositories.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository repository;
    private final VacancyService vacancyService;
    private final ReservationEventPublisher eventPublisher;

    public ReservationService(ReservationRepository repository, VacancyService vacancyService, ReservationEventPublisher eventPublisher) {
        this.repository = repository;
        this.vacancyService = vacancyService;
        this.eventPublisher = eventPublisher;
    }

    public Reservation findEntityById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException("Reservation not found."));
    }

    public Reservation save(ReservationDTO input) {
        Reservation reservation = new Reservation();
        var vacancy = this.vacancyService.findEntityById(input.vacancyId());
        vacancy.setStatus(VacancyStatusEnum.RESERVED);
        reservation.setVacancy(vacancy);
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
        reservation.getVacancy().setStatus(VacancyStatusEnum.AVAILABLE);
        repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CANCELED, reservation.getId());
        return null;
    }
}
