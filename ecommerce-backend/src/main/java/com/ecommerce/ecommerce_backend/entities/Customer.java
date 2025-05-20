package com.ecommerce.ecommerce_backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Customer extends User {


    private String shippingAddress;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime lastLogin;

    @Temporal(TemporalType.TIMESTAMP)
private LocalDateTime createdAt;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")

    @Temporal(TemporalType.TIMESTAMP)
    private Boolean isDeleted = false;

    // Getter and Setter
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders ;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();;

    public void addReview(Review review) {
        reviews.add(review);
        review.setUser(this);
    }
    public List<Review> getReviews() {
        return reviews;
    }
    @Override
    public String getRole() {
        return "ROLE_CUSTOMER";
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Cart getCart() {
        return cart;
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

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
