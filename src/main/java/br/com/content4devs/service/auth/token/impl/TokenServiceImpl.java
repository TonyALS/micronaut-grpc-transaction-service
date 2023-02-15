package br.com.content4devs.service.auth.token.impl;

import br.com.content4devs.exceptions.AccessDeniedException;
import br.com.content4devs.exceptions.UnauthenticatedException;
import br.com.content4devs.model.User;
import br.com.content4devs.service.auth.token.ITokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Singleton
public class TokenServiceImpl implements ITokenService {

    private final Key key;

    public TokenServiceImpl(@Value("${token.server.secret}") String secret) {
        this.key = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    @Override
    public Jws<Claims> parseJwt(String jwtString) {
        try {
            if (Objects.isNull(jwtString)) {
                throw new UnauthenticatedException("Token not found");
            }
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtString);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AccessDeniedException("Token expired");
        } catch (SignatureException signatureException) {
            throw new AccessDeniedException("Signature not valid");
        }
    }
}
