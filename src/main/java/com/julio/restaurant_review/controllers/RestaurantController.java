package com.julio.restaurant_review.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.FindVacanciesRequestDTO;
import com.julio.restaurant_review.model.dto.FindVacanciesResponseDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantResponseDTO;
import com.julio.restaurant_review.model.dto.RestaurantConfigurationDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.dto.RestaurantDetailsDTO;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.services.RestaurantService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService service;
    private final ObjectMapper objectMapper;

    public RestaurantController(RestaurantService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/configuration")
    public ResponseEntity<RestaurantConfigurationDTO> findConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findConfigurationById(id));
    }

    @PostMapping("/paginated")
    public ResponseEntity<Page<PaginatedRestaurantResponseDTO>> findAllPaginated(@ParameterObject Pageable pageable,
                                                                                 @RequestBody @Valid FilterRestaurantDTO filter) {
        return ResponseEntity.ok(service.findAllPaginated(filter, pageable));
    }

    @GetMapping("/{id}/vacancies")
    public ResponseEntity<FindVacanciesResponseDTO> findAllPaginated(@PathVariable Long id,
                                                                     @Valid @ModelAttribute FindVacanciesRequestDTO query) {
        return ResponseEntity.ok(service.generateVacancies(id, query));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Restaurant> save(@RequestPart("images") MultipartFile[] images, @RequestPart("body") String body) throws JsonProcessingException {
        RestaurantDTO input = objectMapper.readValue(body, RestaurantDTO.class);
        return new ResponseEntity<>(service.save(input, images), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}/configuration")
    public ResponseEntity<Void> updateConfiguration(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantConfigurationDTO input
    ) {
        service.updateConfiguration(restaurantId, input);
        return ResponseEntity.noContent().build();
    }
}
