package com.julio.restaurant_review.services;

import com.julio.restaurant_review.controllers.FilesController;
import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.RestaurantDetailsDTO;
import com.julio.restaurant_review.model.dto.ImageInfoDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantResponseDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.dto.ReviewDetailDTO;
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

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
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

    private static ImageInfoDTO getImageInfoDTO(Path path) {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
                .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
        return new ImageInfoDTO(filename, url);
    }

    public RestaurantDetailsDTO findById(Long id) {
        var restaurant = findEntityById(id);
        var imagesInfo = getImagesInfoByFilenames(restaurant.getImages().stream().map(Image::getFilename).collect(Collectors.toSet()));
        return new RestaurantDetailsDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                Address.toDTO(restaurant.getAddress()),
                imagesInfo
        );
    }

    public Restaurant findEntityById(Long id) {
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
                .map(path -> getImageInfoDTO(path))
                .collect(Collectors.toSet());
    }

    private ImageInfoDTO getImageInfoByFilename(String filename) {
        return Optional.of(storageService
                        .loadFilePath(filename))
                .map(path -> getImageInfoDTO(path))
                .orElseThrow(() -> new NotFoundException("Image not found"));
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
