package com.julio.restaurant_review.application.service.auth;

import com.julio.restaurant_review.application.port.in.usecases.auth.FindRoleByNameUseCase;
import com.julio.restaurant_review.application.port.out.persistence.RolePersistencePort;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import com.julio.restaurant_review.domain.model.Role;
import org.springframework.stereotype.Component;

@Component
public class FindRoleByNameUseCaseImpl implements FindRoleByNameUseCase {

    private final RolePersistencePort roleRepository;

    public FindRoleByNameUseCaseImpl(RolePersistencePort roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role execute(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
