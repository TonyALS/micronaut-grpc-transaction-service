package br.com.content4devs.service.user.impl;

import br.com.content4devs.dto.user.UserRequestDto;
import br.com.content4devs.dto.user.UserResponseDto;
import br.com.content4devs.model.User;
import br.com.content4devs.repository.IUserRepository;
import br.com.content4devs.service.user.IUserService;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.function.Function;

@Singleton
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;

    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDto create(UserRequestDto requestDto) {
        String hashpw = BCrypt.hashpw(requestDto.password(), BCrypt.gensalt());
        User user = new User(requestDto.name(), requestDto.email(), hashpw, requestDto.role());
        User userCreated = repository.save(user);
        return toUserResponseDto.apply(userCreated);
    }

    @Override
    public UserResponseDto findByEmail(String email) {
        User user = repository.findByEmail(email).orElse(null);
        return toUserResponseDto.apply(user);
    }

    private final Function<User, UserResponseDto> toUserResponseDto =
            user -> new UserResponseDto(user.getId(), user.getName(),
                    user.getEmail(), user.getRole());
}
