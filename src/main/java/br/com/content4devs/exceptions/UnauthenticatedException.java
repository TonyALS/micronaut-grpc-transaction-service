package br.com.content4devs.exceptions;

import io.grpc.Status;

public class UnauthenticatedException extends BaseBusinessException {

    public UnauthenticatedException(String message) {
        super(message);
    }

    @Override
    public Status.Code errorCode() {
        return Status.Code.UNAUTHENTICATED;
    }
}
