package com.ecommerce.ecommerce_backend.enums;

public enum StatusOrder
{

    PENDING,       // En attente de paiement
    PROCESSING,    // Paiement validé, en préparation
    SHIPPED,       // Expédié
    DELIVERED,     // Livré
    CANCELLED,     // Annulé
    REFUNDED
}
