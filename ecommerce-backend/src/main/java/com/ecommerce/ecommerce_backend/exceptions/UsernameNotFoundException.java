package com.ecommerce.ecommerce_backend.exceptions;





public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
