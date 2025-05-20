package com.ecommerce.ecommerce_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
    @Column(nullable = false, length = 255)
    private String name;

    @Email(message = "Doit être une adresse email valide")
    @NotBlank(message = "L'email est obligatoire")
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    // Méthode abstraite pour différencier les rôles
    public abstract String getRole();

    // Méthode appelée automatiquement avant insertion
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void setLastLoginNow() {
        this.lastLogin = LocalDateTime.now();
    }

    // Méthode pour stocker un mot de passe déjà hashé
    public void hashPassword(String encodedPassword) {
        if (!encodedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Mot de passe non chiffré");
        }
        this.password = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
