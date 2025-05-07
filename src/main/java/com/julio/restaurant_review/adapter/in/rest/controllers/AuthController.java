package com.julio.restaurant_review.adapter.in.rest.controllers;

import com.julio.restaurant_review.adapter.in.rest.dtos.AuthRequestDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.RegisterRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.AuthResponseDTO;
import com.julio.restaurant_review.application.port.in.usecases.auth.LoginUseCase;
import com.julio.restaurant_review.application.port.in.usecases.auth.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO input) {
        return ResponseEntity.ok(loginUseCase.execute(input));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO input) {
        registerUserUseCase.execute(input);
        return ResponseEntity.ok().build();
    }
}
