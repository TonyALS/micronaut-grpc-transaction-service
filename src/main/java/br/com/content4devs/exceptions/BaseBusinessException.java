package br.com.content4devs.exceptions;

import io.grpc.Status;

public abstract class BaseBusinessException extends RuntimeException {

    public BaseBusinessException(String message) {
        super(message);
    }
    public abstract Status.Code errorCode();
}
