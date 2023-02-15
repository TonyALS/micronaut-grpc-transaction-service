package br.com.content4devs.exceptions;

import io.grpc.Status;

public class TransactionTypeException extends BaseBusinessException {

    public TransactionTypeException(String message) {
        super(message);
    }

    @Override
    public Status.Code errorCode() {
        return Status.Code.INVALID_ARGUMENT;
    }
}
