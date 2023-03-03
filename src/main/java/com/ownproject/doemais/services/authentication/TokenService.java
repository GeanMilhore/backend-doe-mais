package com.ownproject.doemais.services.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ownproject.doemais.models.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String gerarToken(Usuario usuario){
        try{
            var algoritmo =  Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("Api Doe+")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token jwt", exception);
        }
    }

    public String getSubject(String tokenJwt){
        try{
            var algoritmo =  Algorithm.HMAC256(secretKey);
            return JWT.require(algoritmo)
                    .withIssuer("Api Doe+")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
