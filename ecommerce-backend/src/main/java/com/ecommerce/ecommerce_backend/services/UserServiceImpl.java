package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.DailyRegistrationDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.CustomerDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserStatsDTO;
import com.ecommerce.ecommerce_backend.entities.Customer;
import com.ecommerce.ecommerce_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository customerRepository;


    @Autowired
    public UserServiceImpl(UserRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllUsers() {
        List<Customer> users = customerRepository.findByIsDeletedFalse();
        return users.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserStatsDTO calculateStats() {
        // Création de l'objet de retour
        UserStatsDTO stats = new UserStatsDTO();

        // Envoi vers le service Flask pour la prédiction
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/predict-registrations";

        // Récupération des enregistrements quotidiens
        List<DailyRegistrationDTO> registrations = customerRepository.getRegistrationsPerDay();

        List<Map<String, Object>> history = registrations.stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getDate().toString());
                    map.put("count", e.getCount());
                    return map;
                })
                .collect(Collectors.toList());

        // Création de la requête HTTP
        Map<String, Object> payload = Map.of("registrations", history);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload);

        // Envoi de la requête POST et récupération de la prédiction
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        // Extraction de la valeur prédite
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Object predictedObj = response.getBody().get("predicted_tomorrow");
            if (predictedObj instanceof Integer) {
                stats.setPredictedRegistrationsTomorrow((Integer) predictedObj);
            } else if (predictedObj instanceof Number) {
                stats.setPredictedRegistrationsTomorrow(((Number) predictedObj).intValue());
            }
        }

        return stats;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setCreatedAt(customer.getCreatedAt());  // c’est déjà un LocalDateTime
        dto.setLastLogin(customer.getLastLogin());
        return dto;
    }


}
