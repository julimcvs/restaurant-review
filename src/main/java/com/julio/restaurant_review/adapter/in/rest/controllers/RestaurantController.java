package com.julio.restaurant_review.adapter.in.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindAllRestaurantsPaginatedUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantConfigurationUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantDetailsUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantVacanciesUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.SaveRestaurantUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.UpdateRestaurantConfigurationUseCase;
import com.julio.restaurant_review.adapter.in.rest.dtos.FilterRestaurantDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.FindVacanciesRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.GetVacanciesResponseDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.PaginatedRestaurantResponseDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.PaginationRequestDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantConfigurationDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.RestaurantDetailsDTO;
import com.julio.restaurant_review.domain.model.Restaurant;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final GetRestaurantDetailsUseCase getRestaurantDetailsUseCase;
    private final GetRestaurantConfigurationUseCase getRestaurantConfigurationUseCase;
    private final FindAllRestaurantsPaginatedUseCase findAllRestaurantsPaginatedUseCase;
    private final GetRestaurantVacanciesUseCase getRestaurantVacanciesUseCase;
    private final UpdateRestaurantConfigurationUseCase updateRestaurantConfigurationUseCase;
    private final SaveRestaurantUseCase saveRestaurantUseCase;
    private final ObjectMapper objectMapper;

    public RestaurantController(GetRestaurantDetailsUseCase getRestaurantDetailsUseCase, GetRestaurantConfigurationUseCase getRestaurantConfigurationUseCase, FindAllRestaurantsPaginatedUseCase findAllRestaurantsPaginatedUseCase, GetRestaurantVacanciesUseCase getRestaurantVacanciesUseCase, UpdateRestaurantConfigurationUseCase updateRestaurantConfigurationUseCase, SaveRestaurantUseCase saveRestaurantUseCase, ObjectMapper objectMapper) {
        this.getRestaurantDetailsUseCase = getRestaurantDetailsUseCase;
        this.getRestaurantConfigurationUseCase = getRestaurantConfigurationUseCase;
        this.findAllRestaurantsPaginatedUseCase = findAllRestaurantsPaginatedUseCase;
        this.getRestaurantVacanciesUseCase = getRestaurantVacanciesUseCase;
        this.updateRestaurantConfigurationUseCase = updateRestaurantConfigurationUseCase;
        this.saveRestaurantUseCase = saveRestaurantUseCase;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(getRestaurantDetailsUseCase.execute(id));
    }

    @GetMapping("/{id}/configuration")
    public ResponseEntity<RestaurantConfigurationDTO> findConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(getRestaurantConfigurationUseCase.execute(id));
    }

    @PostMapping("/paginated")
    public ResponseEntity<Page<PaginatedRestaurantResponseDTO>> findAllPaginated(@ModelAttribute PaginationRequestDTO paginationRequest,
                                                                                 @RequestBody @Valid FilterRestaurantDTO filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationRequest.direction()), paginationRequest.sort());
        Pageable pageable = PageRequest.of(paginationRequest.page(), paginationRequest.size(), sort);
        return ResponseEntity.ok(findAllRestaurantsPaginatedUseCase.execute(filter, pageable));
    }

    @GetMapping("/{id}/vacancies")
    public ResponseEntity<GetVacanciesResponseDTO> findAllPaginated(@PathVariable Long id,
                                                                    @Valid @ModelAttribute FindVacanciesRequestDTO query) {
        return ResponseEntity.ok(getRestaurantVacanciesUseCase.execute(id, query));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Restaurant> save(@RequestPart("images") MultipartFile[] images, @RequestPart("body") String body) throws JsonProcessingException {
        RestaurantDTO input = objectMapper.readValue(body, RestaurantDTO.class);
        return new ResponseEntity<>(saveRestaurantUseCase.execute(input, images), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}/configuration")
    public ResponseEntity<Void> updateConfiguration(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantConfigurationDTO input
    ) {
        updateRestaurantConfigurationUseCase.execute(restaurantId, input);
        return ResponseEntity.noContent().build();
    }
}
