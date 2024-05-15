package com.simplifiedpayment.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.simplifiedpayment.data.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    private final String issuer = "simplified-payment";
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            logger.info("Token gerado para o usuário: {}", user.getEmail());
            return token;
        } catch (JWTCreationException e) {
            logger.error("Erro ao gerar token para o usuário: {}", user.getEmail(), e);
            throw new RuntimeException("Error while generating Token ", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String login = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
            logger.info("Token validado para o usuário: {}", login);
            return login;
        } catch (JWTVerificationException e) {
            logger.error("Erro ao validar token ", e);
            throw new RuntimeException("Error while validating Token ", e);
        }
    }

    private Instant generateExpirationDate() {
        Instant expirationDate = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        logger.info("Data de expiração do token gerada: {}", expirationDate);
        return expirationDate;
    }
}
