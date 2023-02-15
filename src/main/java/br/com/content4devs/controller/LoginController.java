package br.com.content4devs.controller;

import br.com.content4devs.dto.login.LoginRequestDto;
import br.com.content4devs.dto.login.LoginResponseDto;
import br.com.content4devs.service.auth.login.impl.LoginServiceImpl;
import br.com.content4devs.v1.auth.AuthServiceGrpc;
import br.com.content4devs.v1.auth.LoginRequest;
import br.com.content4devs.v1.auth.LoginResponse;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;

@GrpcService
public class LoginController extends AuthServiceGrpc.AuthServiceImplBase {

    private final LoginServiceImpl loginServiceImpl;

    public LoginController(LoginServiceImpl loginServiceImpl) {
        this.loginServiceImpl = loginServiceImpl;
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        LoginRequestDto loginRequestDto = new LoginRequestDto(request.getEmail(), request.getPassword());
        LoginResponseDto loginResponseDto = loginServiceImpl.login(loginRequestDto);
        responseObserver.onNext(LoginResponse.newBuilder()
                .setToken(loginResponseDto.token()).build());
        responseObserver.onCompleted();
    }
}
