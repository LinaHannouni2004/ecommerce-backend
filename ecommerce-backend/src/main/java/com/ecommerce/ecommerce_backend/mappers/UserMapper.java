package com.ecommerce.ecommerce_backend.mappers;

import com.ecommerce.ecommerce_backend.dtos.UserDtos.AdminDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.CustomerDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserRegisterDTO;
import com.ecommerce.ecommerce_backend.entities.Admin;
import com.ecommerce.ecommerce_backend.entities.Customer;
import com.ecommerce.ecommerce_backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserBaseDTO toBaseDTO(User user) {
        UserBaseDTO dto;
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            dto = new AdminDTO();
        } else if ("CUSTOMER".equalsIgnoreCase(user.getRole()) && user instanceof Customer customer) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setShippingAddress(customer.getShippingAddress());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            customerDTO.setBirthDate(customer.getBirthDate());
            dto = customerDTO;
        } else {
            // fallback
            dto = new UserBaseDTO() {}; // Anonymous subclass to avoid instantiating abstract class
        }

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public static User toEntity(UserRegisterDTO dto) {
        if ("ADMIN".equalsIgnoreCase(dto.getUserType())) {
            Admin admin = new Admin();
            admin.setName(dto.getName());
            admin.setEmail(dto.getEmail());
            admin.setPassword(dto.getPassword());
            admin.setRole("ADMIN");
            return admin;
        } else {
            Customer customer = new Customer();
            customer.setName(dto.getName());
            customer.setEmail(dto.getEmail());
            customer.setPassword(dto.getPassword());
            customer.setRole("CUSTOMER");
            return customer;
        }
    }
}
