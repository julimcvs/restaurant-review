package com.julio.restaurant_review.application.service.reservations;

import com.julio.restaurant_review.application.port.in.usecases.reservations.ConfirmReservationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reservations.FindReservationByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.ReservationPersistencePort;
import com.julio.restaurant_review.application.port.out.producers.ReservationEventPublisher;
import com.julio.restaurant_review.domain.enums.ReservationEventTypeEnum;
import com.julio.restaurant_review.domain.enums.ReservationStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class ConfirmReservationUseCaseImpl implements ConfirmReservationUseCase {
    private final ReservationPersistencePort repository;
    private final ReservationEventPublisher eventPublisher;
    private final FindReservationByIdUseCase findReservationByIdUseCase;

    public ConfirmReservationUseCaseImpl(ReservationPersistencePort repository, ReservationEventPublisher eventPublisher, FindReservationByIdUseCase findReservationByIdUseCase) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.findReservationByIdUseCase = findReservationByIdUseCase;
    }

    @Override
    public Void execute(Long id) {
        var reservation = findReservationByIdUseCase.execute(id);
        reservation.setStatus(ReservationStatusEnum.CONFIRMED);
        repository.save(reservation);
        this.eventPublisher.publishReservationEvent(ReservationEventTypeEnum.CONFIRMED, reservation.getId());
        return null;
    }
}
