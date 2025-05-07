package com.julio.restaurant_review.application.service.auth;

import com.julio.restaurant_review.adapter.in.rest.dtos.AuthRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.AuthResponseDTO;
import com.julio.restaurant_review.application.port.in.usecases.auth.LoginUseCase;
import com.julio.restaurant_review.application.port.out.persistence.UserPersistencePort;
import com.julio.restaurant_review.application.port.out.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserPersistencePort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUseCaseImpl(UserPersistencePort userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO execute(AuthRequestDTO input) {
        var user = userRepository.findByUsername(input.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }
}
