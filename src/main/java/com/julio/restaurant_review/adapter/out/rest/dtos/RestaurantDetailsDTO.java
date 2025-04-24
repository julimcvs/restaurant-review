package com.julio.restaurant_review.adapter.out.rest.dtos;

import com.julio.restaurant_review.adapter.in.rest.dtos.AddressDTO;
import com.julio.restaurant_review.application.dtos.ImageInfoDTO;

import java.util.Set;

public class RestaurantDetailsDTO {
    private final Long id;
    private final String name;
    private final String description;
    private final AddressDTO address;
    private final Set<ImageInfoDTO> images;

    public RestaurantDetailsDTO(Long id, String name, String description, AddressDTO address, Set<ImageInfoDTO> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public Set<ImageInfoDTO> getImages() {
        return images;
    }
}
