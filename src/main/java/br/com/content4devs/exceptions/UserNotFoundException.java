package br.com.content4devs.exceptions;

import io.grpc.Status;

public class UserNotFoundException extends BaseBusinessException {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public Status.Code errorCode() {
        return Status.Code.NOT_FOUND;
    }
}
