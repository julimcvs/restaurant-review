package com.julio.restaurant_review.application.service.reservations;

import com.julio.restaurant_review.application.port.in.usecases.reservations.CancelReservationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reservations.FindReservationByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.application.port.out.producers.ReservationEventPublisher;
import com.julio.restaurant_review.domain.enums.ReservationEventTypeEnum;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class CancelReservationUseCaseImpl implements CancelReservationUseCase {
    private final ReservationPersistencePort repository;
    private final FindReservationByIdUseCase findReservationByIdUseCase;
    private final ReservationEventPublisher eventPublisher;

    public CancelReservationUseCaseImpl(ReservationPersistencePort repository, FindReservationByIdUseCase findReservationByIdUseCase, ReservationEventPublisher eventPublisher) {
        this.repository = repository;
        this.findReservationByIdUseCase = findReservationByIdUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Void execute(Long id) {
        var reservation = findReservationByIdUseCase.execute(id);
        reservation.setStatus(ReservationStatusEnum.CANCELED);
        repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CANCELED, reservation.getId());
        return null;
    }
}
