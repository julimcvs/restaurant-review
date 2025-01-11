package com.julio.restaurant_review.controllers;

import com.julio.restaurant_review.model.dto.ReviewDTO;
import com.julio.restaurant_review.model.entity.Review;
import com.julio.restaurant_review.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Review> save(@RequestBody @Valid ReviewDTO input) {
        return new ResponseEntity<>(service.save(input), HttpStatus.CREATED);
    }
}
