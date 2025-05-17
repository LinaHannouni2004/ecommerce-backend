package com.ecommerce.ecommerce_backend.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING,length = 10)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Email(message = "Doit être une adresse email valide")
    @NotBlank(message = "L'email est obligatoire")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    private String role;
    // Méthode abstraite pour différencier les rôles
    public abstract String getRole();



    // Méthode pour hasher le mot de passe (à appeler avant persistance)
    public void hashPassword(String encodedPassword) {
        // S'assurer qu'on utilise BCrypt
        if (!encodedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Mot de passe non chiffré");
        }
        this.password = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
