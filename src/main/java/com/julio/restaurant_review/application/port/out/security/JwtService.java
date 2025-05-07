package com.julio.restaurant_review.application.port.out.security;

import com.julio.restaurant_review.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(User user);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
