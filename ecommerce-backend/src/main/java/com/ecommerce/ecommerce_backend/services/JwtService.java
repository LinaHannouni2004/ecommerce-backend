package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;


import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "ta_clé_secrète_très_longue_qui_est_au_moins_256_bits";

    public String generateToken(User user) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .signWith(key)
                .compact();
    }
}
