package com.ecommerce.ecommerce_backend.dtos.UserDtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends UserBaseDTO {
    private String shippingAddress;
    private String phoneNumber;
    private LocalDate birthDate;


    @Temporal(TemporalType.TIMESTAMP)
private LocalDateTime CreatedAt;


    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastLogin;

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    private Boolean isDeleted = false;

    // Getter and Setter
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}