package com.apirestfull.user_api.service;

import com.apirestfull.user_api.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${app.jwt.expiration}")
    private long expiracao;

    @Value("${app.jwt.secret}")
    private String assinatura;

    private Algorithm jwtAlgorithm() {
        try {
            return Algorithm.HMAC256(this.assinatura);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Erro ao criar algoritmo JWT", e);
        }
    }

    public JWTVerifier verifier() {
        return JWT.require(this.jwtAlgorithm()).build();
    }

    public DecodedJWT decode(String token) {
        return this.verifier().verify(token);
    }

    public boolean tokenIsValid(String token) {
        try {
            DecodedJWT claims = this.decode(token);
            LocalDateTime expirationDate = claims.getExpiresAt()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            return !LocalDateTime.now().isAfter(expirationDate);
        } catch (Exception e) {
            return false;
        }
    }

    public String generateToken(Usuario usuario) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plus(Duration.ofMillis(expiracao));
        Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(usuario.getId().toString())
                .withClaim("nome", usuario.getNome())
                .withClaim("email", usuario.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(this.jwtAlgorithm());
    }
}
