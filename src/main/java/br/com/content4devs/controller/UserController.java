package br.com.content4devs.controller;

import br.com.content4devs.dto.user.UserRequestDto;
import br.com.content4devs.dto.user.UserResponseDto;
import br.com.content4devs.service.user.IUserService;
import br.com.content4devs.v1.user.CreateUserServiceGrpc;
import br.com.content4devs.v1.user.Role;
import br.com.content4devs.v1.user.UserRequest;
import br.com.content4devs.v1.user.UserResponse;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;

@GrpcService
public class UserController extends CreateUserServiceGrpc.CreateUserServiceImplBase {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserRequestDto requestDto = new UserRequestDto(request.getName(), request.getEmail(), request.getPassword(), request.getRole().name());
        UserResponseDto userResponseDto = userService.create(requestDto);
        responseObserver.onNext(UserResponse.newBuilder()
                        .setId(userResponseDto.id())
                        .setName(userResponseDto.name())
                        .setEmail(userResponseDto.email())
                        .setRole(Role.valueOf(userResponseDto.role()))
                .build());
        responseObserver.onCompleted();
    }
}
