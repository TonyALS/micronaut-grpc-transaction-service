package br.com.content4devs.service.user;

import br.com.content4devs.dto.user.UserRequestDto;
import br.com.content4devs.dto.user.UserResponseDto;

public interface IUserService {
    UserResponseDto create(UserRequestDto requestDto);
    UserResponseDto findByEmail(String email);
}
