package com.julio.restaurant_review.adapter.in.rest.controllers;

import com.julio.restaurant_review.application.port.in.usecases.reviews.FindAllReviewsPaginatedByRestaurantUseCase;
import com.julio.restaurant_review.application.port.in.usecases.reviews.SaveReviewUseCase;
import com.julio.restaurant_review.adapter.in.rest.dtos.PaginationRequestDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.ReviewDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.ReviewDetailDTO;
import com.julio.restaurant_review.domain.model.Review;
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
    private final SaveReviewUseCase saveReviewUseCase;
    private final FindAllReviewsPaginatedByRestaurantUseCase findAllReviewsPaginatedByRestaurantUseCase;

    public ReviewController(SaveReviewUseCase saveReviewUseCase, FindAllReviewsPaginatedByRestaurantUseCase findAllReviewsPaginatedByRestaurantUseCase) {
        this.saveReviewUseCase = saveReviewUseCase;
        this.findAllReviewsPaginatedByRestaurantUseCase = findAllReviewsPaginatedByRestaurantUseCase;
    }

    @PostMapping("restaurant/{restaurantId}/paginated")
    public ResponseEntity<Page<ReviewDetailDTO>> findAllPaginated(@ModelAttribute PaginationRequestDTO paginationRequest,
                                                                  @PathVariable Long restaurantId) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.direction()), paginationRequest.sort());
        Pageable pageable = PageRequest.of(paginationRequest.page(), paginationRequest.size(), sort);
        return ResponseEntity.ok(findAllReviewsPaginatedByRestaurantUseCase.execute(restaurantId, pageable));
    }

    @PostMapping
    public ResponseEntity<Review> save(@RequestBody @Valid ReviewDTO input) {
        return new ResponseEntity(saveReviewUseCase.execute(input), HttpStatus.CREATED);
    }
}
