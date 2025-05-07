package com.julio.restaurant_review.application.port.in.usecases.auth;

import com.julio.restaurant_review.domain.model.Role;

public interface FindRoleByNameUseCase {
    Role execute(String name);
}
