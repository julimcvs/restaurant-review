package com.julio.restaurant_review.application.port.in.usecases.auth;

import com.julio.restaurant_review.adapter.in.rest.dtos.RegisterRequestDTO;

public interface RegisterUserUseCase {
    Void execute(RegisterRequestDTO input);
}
