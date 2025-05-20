package com.ecommerce.ecommerce_backend.web.controllers;


import com.ecommerce.ecommerce_backend.dtos.UserDtos.CustomerDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserStatsDTO;
import com.ecommerce.ecommerce_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<CustomerDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/stats")
    public Map<String, List<Map<String, Object>>> getUserStats() {
        // Simule des données, tu dois récupérer la vraie info en base
        List<Map<String, Object>> registrations = List.of(
                Map.of("date", "2025-05-15", "count", 5),
                Map.of("date", "2025-05-16", "count", 10),
                Map.of("date", "2025-05-17", "count", 7)
        );
        List<Map<String, Object>> logins = List.of(
                Map.of("date", "2025-05-15", "count", 15),
                Map.of("date", "2025-05-16", "count", 20),
                Map.of("date", "2025-05-17", "count", 12)
        );

        return Map.of(
                "registrations", registrations,
                "logins", logins
        );
    }


}
