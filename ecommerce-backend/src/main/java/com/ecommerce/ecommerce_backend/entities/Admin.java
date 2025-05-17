package com.ecommerce.ecommerce_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
@Data
@EqualsAndHashCode(callSuper = true)

public class Admin extends User {

    @OneToMany(mappedBy = "reviewedBy")
    private List<Review> moderatedReviews ;

    public List<Review> getModeratedReviews() {
        return moderatedReviews;
    }

    public void setModeratedReviews(List<Review> moderatedReviews) {
        this.moderatedReviews = moderatedReviews;
    }

    @Override
    public String getRole() {
        return "ROLE_ADMIN";
    }
}
