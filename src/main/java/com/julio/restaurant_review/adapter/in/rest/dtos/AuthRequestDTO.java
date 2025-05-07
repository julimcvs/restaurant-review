package com.julio.restaurant_review.adapter.in.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AuthRequestDTO(
        @NotBlank @Length(min = 1, max = 255) @Email String email,
        @NotBlank @Length(min = 0, max = 255) String password
) {
}
