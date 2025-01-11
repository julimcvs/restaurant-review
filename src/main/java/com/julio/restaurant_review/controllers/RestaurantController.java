package com.julio.restaurant_review.controllers;

import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping("/paginated")
    public ResponseEntity<Page<PaginatedRestaurantDTO>> findAllPaginated(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                         @RequestParam(name = "sort", defaultValue = "id") String sortParam,
                                                                         @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                                                                         @RequestBody @Valid FilterRestaurantDTO filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortParam);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(service.findAllPaginated(filter, pageable));
    }

    @PostMapping
    public ResponseEntity<Restaurant> save(@RequestBody @Valid RestaurantDTO input) {
        return new ResponseEntity<>(service.save(input), HttpStatus.CREATED);
    }
}
