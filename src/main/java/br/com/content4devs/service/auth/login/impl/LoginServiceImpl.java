package br.com.content4devs.service.auth.login.impl;

import br.com.content4devs.dto.login.LoginRequestDto;
import br.com.content4devs.dto.login.LoginResponseDto;
import br.com.content4devs.exceptions.UnauthenticatedException;
import br.com.content4devs.exceptions.UserNotFoundException;
import br.com.content4devs.model.User;
import br.com.content4devs.repository.IUserRepository;
import br.com.content4devs.service.auth.login.ILoginService;
import br.com.content4devs.service.auth.token.ITokenService;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

@Singleton
public class LoginServiceImpl implements ILoginService {

    private final IUserRepository repository;
    private final ITokenService tokenService;

    public LoginServiceImpl(IUserRepository repository, ITokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = repository.findByEmail(requestDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!isValidPassword(requestDto.password(), user.getPassword())) {
            throw new UnauthenticatedException("Invalid credentials");
        }
        String token = tokenService.generateToken(user);
        return new LoginResponseDto(token);
    }

    private boolean isValidPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
