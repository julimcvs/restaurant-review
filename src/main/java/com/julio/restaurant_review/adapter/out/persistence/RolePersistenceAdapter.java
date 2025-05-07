package com.julio.restaurant_review.adapter.out.persistence;

import com.julio.restaurant_review.adapter.out.jpa.RoleRepository;
import com.julio.restaurant_review.adapter.out.jpa.UserRepository;
import com.julio.restaurant_review.application.port.out.persistence.RolePersistencePort;
import com.julio.restaurant_review.application.port.out.persistence.UserPersistencePort;
import com.julio.restaurant_review.domain.model.Role;
import com.julio.restaurant_review.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RolePersistenceAdapter implements RolePersistencePort {
    private RoleRepository repository;

    public RolePersistenceAdapter(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }
}
