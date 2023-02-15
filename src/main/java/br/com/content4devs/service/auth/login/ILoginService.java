package br.com.content4devs.service.auth.login;

import br.com.content4devs.dto.login.LoginRequestDto;
import br.com.content4devs.dto.login.LoginResponseDto;

public interface ILoginService {
    LoginResponseDto login(LoginRequestDto requestDto);
}
