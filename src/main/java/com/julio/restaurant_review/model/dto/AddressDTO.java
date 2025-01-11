package com.julio.restaurant_review.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AddressDTO(
        @NotBlank
        @Length(min = 3, max = 100)
        String city,

        @NotBlank
        @Length(min = 3, max = 100)
        String street,

        @NotBlank
        @Length(min = 3, max = 100)
        String neighborhood,

        @NotBlank
        @Length(min = 1, max = 5)
        String number,

        @NotBlank
        @Length(min = 8, max = 8)
        String zipCode,

        @NotBlank
        @Length(min = 2, max = 2)
        String state,

        @NotBlank
        @Length(min = 3, max = 100)
        String country
) {
}
