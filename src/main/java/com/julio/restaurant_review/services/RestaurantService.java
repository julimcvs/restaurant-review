package com.julio.restaurant_review.services;

import com.julio.restaurant_review.controllers.FilesController;
import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.ImageInfoDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantQueryDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantResponseDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.entity.Address;
import com.julio.restaurant_review.model.entity.Image;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repository;
    private final CategoryService categoryService;
    private final FileStorageService storageService;

    public RestaurantService(RestaurantRepository repository, CategoryService categoryService, FileStorageService storageService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    public Restaurant findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    public Page<PaginatedRestaurantResponseDTO> findAllPaginated(FilterRestaurantDTO filter, Pageable pageable) {
        var output = repository.findAllPaginated(
                filter.name(),
                filter.city(),
                filter.neighborhood(),
                pageable
        );
        var images = repository.findImageFilenamesByRestaurantIds(output.get().map(PaginatedRestaurantQueryDTO::getId).toList());
        return output.map(restaurant -> {
            var restaurantImages = images
                    .stream()
                    .filter(image -> image.getRestaurants().stream().anyMatch(r -> r.getId().equals(restaurant.getId())))
                    .map(Image::getFilename)
                    .collect(Collectors.toSet());
            var imagesInfo = getImagesInfoByFilenames(restaurantImages);
            return new PaginatedRestaurantResponseDTO(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getAverageRating(),
                    restaurant.getTotalReviews(),
                    imagesInfo
            );
        });
    }

    public Restaurant save(RestaurantDTO input, MultipartFile[] images) {
        var restaurant = Restaurant.fromDTO(input);
        if (input.id() != null) {
            restaurant = repository
                    .findById(input.id())
                    .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        saveImages(images, restaurant);
        var address = Address.fromDTO(input.address());
        restaurant.setAddress(address);
        var category = categoryService.findById(input.categoryId());
        restaurant.setCategory(category);
        return repository.save(restaurant);
    }

    private Set<ImageInfoDTO> getImagesInfoByFilenames(Set<String> filenames) {
        return storageService
                .loadAll(filenames)
                .map(path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
                    return new ImageInfoDTO(filename, url);
                }).collect(Collectors.toSet());
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
