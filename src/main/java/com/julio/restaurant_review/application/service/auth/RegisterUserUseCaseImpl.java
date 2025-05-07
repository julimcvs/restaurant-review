package com.julio.restaurant_review.application.service.auth;

import com.julio.restaurant_review.adapter.in.rest.dtos.RegisterRequestDTO;
import com.julio.restaurant_review.application.port.in.usecases.auth.FindRoleByNameUseCase;
import com.julio.restaurant_review.application.port.in.usecases.auth.RegisterUserUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.out.persistence.UserPersistencePort;
import com.julio.restaurant_review.domain.enums.RoleEnum;
import com.julio.restaurant_review.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final UserPersistencePort userRepository;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindRoleByNameUseCase findRoleByNameUseCase;

    public RegisterUserUseCaseImpl(UserPersistencePort userRepository, FindRestaurantByIdUseCase findRestaurantByIdUseCase, FindRoleByNameUseCase findRoleByNameUseCase) {
        this.userRepository = userRepository;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
        this.findRoleByNameUseCase = findRoleByNameUseCase;
    }

    @Override
    public Void execute(RegisterRequestDTO input) {
        var restaurant = findRestaurantByIdUseCase.execute(input.restaurantId());
        var role = findRoleByNameUseCase.execute(String.valueOf(RoleEnum.RESTAURANT_ADMIN));
        var user = new User(
                input.username(),
                input.password(),
                true,
                Set.of(role),
                restaurant
        );
        userRepository.save(user);
        return null;
    }
}
