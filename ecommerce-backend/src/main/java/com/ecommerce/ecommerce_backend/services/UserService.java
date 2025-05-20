package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.UserDtos.CustomerDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserStatsDTO;

import java.util.List;

public interface UserService {
    List<CustomerDTO> getAllUsers();
    UserStatsDTO calculateStats();
}
