package com.julio.restaurant_review.adapter.in.rest.controllers;

import com.julio.restaurant_review.application.port.in.usecases.reservations.CancelReservationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reservations.ConfirmReservationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reservations.SaveReservationUseCase;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReservationDTO;
import com.julio.restaurant_review.domain.model.Reservation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final SaveReservationUseCase saveReservationUseCase;
    private final ConfirmReservationUseCase confirmReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;

    public ReservationController(SaveReservationUseCase saveReservationUseCase, ConfirmReservationUseCase confirmReservationUseCase, CancelReservationUseCase cancelReservationUseCase) {
        this.saveReservationUseCase = saveReservationUseCase;
        this.confirmReservationUseCase = confirmReservationUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
    }


    @PostMapping
    public ResponseEntity<Reservation> save(@RequestBody @Valid ReservationDTO input) {
        return new ResponseEntity<>(saveReservationUseCase.execute(input), HttpStatus.CREATED);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(confirmReservationUseCase.execute(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(cancelReservationUseCase.execute(id));
    }
}
