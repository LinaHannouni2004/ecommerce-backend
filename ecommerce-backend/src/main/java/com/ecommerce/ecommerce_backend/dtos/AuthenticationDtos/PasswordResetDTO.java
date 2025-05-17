package com.ecommerce.ecommerce_backend.dtos.AuthenticationDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// PasswordResetDTO.java
@Data
public class PasswordResetDTO {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String newPassword;

    @NotBlank
    private String token;
}
