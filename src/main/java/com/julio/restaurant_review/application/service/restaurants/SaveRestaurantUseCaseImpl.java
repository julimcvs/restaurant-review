package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.categories.FindCategoryUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.SaveRestaurantUseCase;
import com.julio.restaurant_review.application.port.out.persistence.RestaurantPersistencePort;
import com.julio.restaurant_review.application.port.out.storage.FileStoragePort;
import com.julio.restaurant_review.domain.model.Address;
import com.julio.restaurant_review.domain.model.Image;
import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.domain.exceptions.BadRequestException;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SaveRestaurantUseCaseImpl implements SaveRestaurantUseCase {
    private final RestaurantPersistencePort repository;
    private final FindCategoryUseCase findCategoryUseCase;
    private final FileStoragePort storageService;

    public SaveRestaurantUseCaseImpl(RestaurantPersistencePort repository, FindCategoryUseCase findCategoryUseCase, FileStoragePort storageService) {
        this.repository = repository;
        this.findCategoryUseCase = findCategoryUseCase;
        this.storageService = storageService;
    }

    @Override
    public Restaurant execute(RestaurantDTO input, MultipartFile[] images) {
        var restaurant = Restaurant.fromDTO(input);
        if (input.id() != null) {
            restaurant = repository
                    .findById(input.id())
                    .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        saveImages(images, restaurant);
        var address = Address.fromDTO(input.address());
        restaurant.setAddress(address);
        var category = findCategoryUseCase.execute(input.categoryId());
        restaurant.setCategory(category);
        return repository.save(restaurant);
    }

    private void saveImages(MultipartFile[] images, Restaurant restaurant) {
        if (images == null || images.length == 0) {
            throw new BadRequestException("You must provide at least one image");
        }
        if (images.length > 10) {
            throw new BadRequestException("You can only provide up to 10 images");
        }
        try {
            Arrays.asList(images).forEach(storageService::save);

        } catch (Exception e) {
            throw new BadRequestException("Could not store the files. Error: " + e.getMessage());
        }

        var imageEntities = Arrays.stream(images)
                .map(file -> new Image(file.getOriginalFilename()))
                .collect(Collectors.toSet());
        restaurant.setImages(imageEntities);
    }
}
