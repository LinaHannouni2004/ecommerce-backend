package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.web.dto.AuthResponse;
import com.ecommerce.ecommerce_backend.web.dto.LoginRequest;
import com.ecommerce.ecommerce_backend.web.dto.RegisterRequest;

public interface AuthService {
    AuthResponse authenticate(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}