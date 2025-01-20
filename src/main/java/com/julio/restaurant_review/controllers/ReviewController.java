package com.julio.restaurant_review.controllers;

import com.julio.restaurant_review.model.dto.PaginationRequestDTO;
import com.julio.restaurant_review.model.dto.ReviewDTO;
import com.julio.restaurant_review.model.dto.ReviewDetailDTO;
import com.julio.restaurant_review.model.entity.Review;
import com.julio.restaurant_review.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("restaurant/{restaurantId}/paginated")
    public ResponseEntity<Page<ReviewDetailDTO>> findAllPaginated(@ModelAttribute PaginationRequestDTO paginationRequest,
                                                                  @PathVariable Long restaurantId) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.direction()), paginationRequest.sort());
        Pageable pageable = PageRequest.of(paginationRequest.page(), paginationRequest.size(), sort);
        return ResponseEntity.ok(service.findPaginatedByRestaurantId(restaurantId, pageable));
    }

    @PostMapping
    public ResponseEntity<Review> save(@RequestBody @Valid ReviewDTO input) {
        return new ResponseEntity<>(service.save(input), HttpStatus.CREATED);
    }
}
