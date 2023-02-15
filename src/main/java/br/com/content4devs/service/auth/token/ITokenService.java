package br.com.content4devs.service.auth.token;

import br.com.content4devs.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface ITokenService {
    String generateToken(User user);
    Jws<Claims> parseJwt(String jwtToken);
}
