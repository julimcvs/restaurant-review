package com.julio.restaurant_review.controllers;

import com.julio.restaurant_review.model.dto.ReservationDTO;
import com.julio.restaurant_review.model.entity.Reservation;
import com.julio.restaurant_review.services.ReservationService;
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
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Reservation> save(@RequestBody @Valid ReservationDTO input) {
        return new ResponseEntity<>(service.save(input), HttpStatus.CREATED);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirm(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(id));
    }
}
