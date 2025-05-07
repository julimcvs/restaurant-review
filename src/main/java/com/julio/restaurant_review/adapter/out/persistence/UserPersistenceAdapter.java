package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.adapter.out.jpa.UserRepository;
import com.julio.restaurant_review.application.port.out.persistence.UserPersistencePort;
import com.julio.restaurant_review.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserPersistencePort {
    private UserRepository repository;

    public UserPersistenceAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
