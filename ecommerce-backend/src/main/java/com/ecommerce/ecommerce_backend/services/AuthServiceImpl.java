package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;
import com.ecommerce.ecommerce_backend.entities.Admin;
import com.ecommerce.ecommerce_backend.entities.Customer;
import com.ecommerce.ecommerce_backend.entities.User;
import com.ecommerce.ecommerce_backend.exceptions.InvalidCredentialsException;
import com.ecommerce.ecommerce_backend.exceptions.UserNotFoundException;
import com.ecommerce.ecommerce_backend.exceptions.UsernameNotFoundException;
import com.ecommerce.ecommerce_backend.repositories.UserRepository;
import com.ecommerce.ecommerce_backend.services.AuthService;
import com.ecommerce.ecommerce_backend.web.dto.AuthResponse;
import com.ecommerce.ecommerce_backend.web.dto.LoginRequest;
import com.ecommerce.ecommerce_backend.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;


@Service

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        System.out.println("Role received: " + request.getRole());  // Ajout du log pour vérifier le rôle

        User user;

        // On utilise request.getRole() pour choisir la bonne classe
        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            user = new Admin();
        } else {
            user = new Customer();
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);  // Assure-toi que le mot de passe est encodé
        user.setRole(request.getRole());

        userRepository.save(user);


        userRepository.save(user);

        return new AuthResponse("fake-jwt-token", null);
    }


    public AuthResponse authenticate(LoginRequest request) {
        try {
            System.out.println("Authenticating: " + request.getEmail());

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            System.out.println("User found: " + user.getEmail());
            System.out.println("Password from request: " + request.getPassword());
            System.out.println("Password from DB: " + user.getPassword());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                System.out.println("Password does not match");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }

            String token = jwtService.generateToken(user);
            System.out.println("JWT generated: " + token);

            UserBaseDTO userDTO = new UserBaseDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole());
            user.setLastLoginNow();

            return new AuthResponse(token, userDTO);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 pour voir le stack trace complet
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during authentication", e);
        }
    }



}
