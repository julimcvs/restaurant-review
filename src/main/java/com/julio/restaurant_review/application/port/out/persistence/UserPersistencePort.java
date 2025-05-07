package com.julio.restaurant_review.application.port.out.persistence;

import com.julio.restaurant_review.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findByUsername(String username);

    User save(User user);
}
