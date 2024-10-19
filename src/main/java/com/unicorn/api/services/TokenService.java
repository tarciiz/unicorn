package com.unicorn.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenService {
    private static final String secret = "sapiens";
    private static final int TOKEN_EXPIRATION_IN_DAYS = 45;

    public String generateToken(String email) {
        try {
            var algorithm = getAlgorithm();

            return JWT.create()
                    .withIssuer("sapiens")
                    .withSubject(email)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro autenticando o usuário.");
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = getAlgorithm();
            return JWT.require(algorithm)
                    .withIssuer("sapiens")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return new String();
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusDays(TOKEN_EXPIRATION_IN_DAYS).toInstant(ZoneOffset.of("-03:00"));
    }

}
