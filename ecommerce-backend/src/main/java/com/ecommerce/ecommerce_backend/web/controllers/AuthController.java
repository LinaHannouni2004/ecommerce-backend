package com.ecommerce.ecommerce_backend.web.controllers;

import com.ecommerce.ecommerce_backend.services.AuthService;
import com.ecommerce.ecommerce_backend.web.dto.AuthResponse;
import com.ecommerce.ecommerce_backend.web.dto.LoginRequest;
import com.ecommerce.ecommerce_backend.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }





    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("Role received: " + request.getRole());
        return ResponseEntity.ok(authService.register(request));
    }
}
