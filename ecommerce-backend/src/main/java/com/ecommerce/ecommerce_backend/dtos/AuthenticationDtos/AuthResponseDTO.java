package com.ecommerce.ecommerce_backend.dtos.AuthenticationDtos;

import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private UserBaseDTO user;
}
