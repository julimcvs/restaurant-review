package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.Role;

import java.util.Optional;

public interface RolePersistencePort {
    Optional<Role> findByName(String name);
}
