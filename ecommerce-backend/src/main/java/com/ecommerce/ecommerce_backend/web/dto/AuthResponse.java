package com.ecommerce.ecommerce_backend.web.dto;


import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;

public class AuthResponse {
    private String token;
    private UserBaseDTO user;

    // Constructeur
    public AuthResponse(String token, UserBaseDTO user) {
        this.token = token;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBaseDTO getUser() {
        return user;
    }

    public void setUser(UserBaseDTO user) {
        this.user = user;
    }
}