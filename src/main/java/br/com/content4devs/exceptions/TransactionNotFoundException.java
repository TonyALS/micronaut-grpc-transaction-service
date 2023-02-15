package br.com.content4devs.exceptions;

import io.grpc.Status;

public class TransactionNotFoundException extends BaseBusinessException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

    @Override
    public Status.Code errorCode() {
        return Status.Code.NOT_FOUND;
    }
}
