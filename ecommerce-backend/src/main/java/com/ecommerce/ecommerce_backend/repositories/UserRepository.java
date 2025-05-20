package com.ecommerce.ecommerce_backend.repositories;

import com.ecommerce.ecommerce_backend.dtos.DailyRegistrationDTO;
import com.ecommerce.ecommerce_backend.entities.Admin;
import com.ecommerce.ecommerce_backend.entities.Customer;
import com.ecommerce.ecommerce_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Pour la modération (récupère seulement les admins)
    @Query("SELECT u FROM Admin u")
    List<Admin> findAllAdmins();

    @Query("SELECT u FROM Customer u LEFT JOIN FETCH u.reviews WHERE u.id = :id")
    Optional<User> findByIdWithReviews(@Param("id") Long id);

    boolean existsByEmail(String email);
    // Use explicit JPQL query instead of derived query
    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false")
    List<Customer> findActiveCustomers();
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.isDeleted = true")
    long countDeletedCustomers();

    // Récupère tous les utilisateurs non supprimés
    List<Customer> findByIsDeletedFalse();

    // Comptage des utilisateurs supprimés
    long countByIsDeletedTrue();

    // Récupère tous les utilisateurs créés à une certaine date
    List<Customer> findByCreatedAtBetween(LocalDate start, LocalDate end);

    // Comptage des connexions aujourd'hui
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.lastLogin >= :today")
    long countByLastLoginAfter(LocalDate today);

    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCustomers();


    @Query("SELECT new com.ecommerce.ecommerce_backend.dtos.DailyRegistrationDTO(u.createdAt, COUNT(u)) " +
            "FROM Customer u GROUP BY u.createdAt")
    List<DailyRegistrationDTO> getRegistrationsPerDay();



    @Query("SELECT DATE(u.lastLogin) AS date, COUNT(u) AS count " +
            "FROM User u " +
            "WHERE u.lastLogin IS NOT NULL " +
            "GROUP BY DATE(u.lastLogin) " +
            "ORDER BY DATE(u.lastLogin)")
    List<Object[]> getLoginsPerDay();


}
