package com.julio.restaurant_review.application.port.in.usecases.auth;

import com.julio.restaurant_review.adapter.in.rest.dtos.AuthRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.AuthResponseDTO;

public interface LoginUseCase {
    AuthResponseDTO execute(AuthRequestDTO input);
}
