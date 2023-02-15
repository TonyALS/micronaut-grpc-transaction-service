package br.com.content4devs.exceptions;

import io.grpc.Status;

public class AccessDeniedException extends BaseBusinessException {

    public AccessDeniedException(String message) {
        super(message);
    }

    @Override
    public Status.Code errorCode() {
        return Status.Code.PERMISSION_DENIED;
    }
}
