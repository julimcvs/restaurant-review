package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindAllRestaurantsPaginatedUseCase;
import com.julio.restaurant_review.application.port.out.persistence.RestaurantPersistencePort;
import com.julio.restaurant_review.application.port.out.storage.FileStoragePort;
import com.julio.restaurant_review.application.util.ImageMapper;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import com.julio.restaurant_review.adapter.in.rest.dtos.FilterRestaurantDTO;
import com.julio.restaurant_review.application.dtos.ImageInfoDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.PaginatedRestaurantResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindAllRestaurantsPaginatedUseCaseImpl implements FindAllRestaurantsPaginatedUseCase {
    private final RestaurantPersistencePort repository;
    private final FileStoragePort storageService;

    public FindAllRestaurantsPaginatedUseCaseImpl(RestaurantPersistencePort repository, FileStoragePort storageService) {
        this.repository = repository;
        this.storageService = storageService;
    }

    @Override
    public Page<PaginatedRestaurantResponseDTO> execute(FilterRestaurantDTO filter, Pageable pageable) {
        var output = repository.findAllPaginated(
                filter.name(),
                filter.city(),
                filter.neighborhood(),
                pageable
        );
        return output.map(restaurant -> {
            var image = repository.findImagesByRestaurantId(restaurant.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("No images were found for restaurant " + restaurant.getName()));
            var imageInfo = getImageInfoByFilename(image.getFilename());
            return new PaginatedRestaurantResponseDTO(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getAverageRating(),
                    restaurant.getTotalReviews(),
                    imageInfo
            );
        });
    }

    private ImageInfoDTO getImageInfoByFilename(String filename) {
        return Optional.of(storageService
                        .loadFilePath(filename))
                .map(ImageMapper::getImageInfoDTO)
                .orElseThrow(() -> new NotFoundException("Image not found"));
    }
}
