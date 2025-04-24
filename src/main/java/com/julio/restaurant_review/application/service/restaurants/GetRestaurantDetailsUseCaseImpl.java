package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantDetailsUseCase;
import com.julio.restaurant_review.application.port.out.storage.FileStoragePort;
import com.julio.restaurant_review.application.util.ImageMapper;
import com.julio.restaurant_review.domain.model.Address;
import com.julio.restaurant_review.domain.model.Image;
import com.julio.restaurant_review.application.dtos.ImageInfoDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.RestaurantDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GetRestaurantDetailsUseCaseImpl implements GetRestaurantDetailsUseCase {
    private final FileStoragePort storageService;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public GetRestaurantDetailsUseCaseImpl(FileStoragePort storageService, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.storageService = storageService;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    @Override
    public RestaurantDetailsDTO execute(Long id) {
        var restaurant = findRestaurantByIdUseCase.execute(id);
        var imagesInfo = getImagesInfoByFilenames(restaurant.getImages().stream().map(Image::getFilename).collect(Collectors.toSet()));
        return new RestaurantDetailsDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                Address.toDTO(restaurant.getAddress()),
                imagesInfo
        );
    }

    private Set<ImageInfoDTO> getImagesInfoByFilenames(Set<String> filenames) {
        return storageService
                .loadAll(filenames)
                .map(ImageMapper::getImageInfoDTO)
                .collect(Collectors.toSet());
    }
}
