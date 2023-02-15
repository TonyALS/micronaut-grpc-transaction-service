package br.com.content4devs.controller.stream;

import br.com.content4devs.dto.transaction.TransactionResponseDto;
import br.com.content4devs.service.transaction.ITransactionService;
import br.com.content4devs.v1.transaction.TransactionRequest;
import br.com.content4devs.v1.transaction.TransactionResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import static br.com.content4devs.functions.TransactionConverter.toTransactionRequestDto;
import static br.com.content4devs.functions.TransactionConverter.toTransactionResponse;


public class CreateTransactionBidirectionalStream implements StreamObserver<TransactionRequest> {

    private final StreamObserver<TransactionResponse> responseObserver;

    private final ITransactionService service;

    public CreateTransactionBidirectionalStream(StreamObserver<TransactionResponse> responseObserver, ITransactionService service) {
        this.responseObserver = responseObserver;
        this.service = service;
    }

    @Override
    public void onNext(TransactionRequest value) {
        TransactionResponseDto transaction = service.createTransaction(toTransactionRequestDto.apply(value));
        TransactionResponse transactionResponse = toTransactionResponse.apply(transaction);
        responseObserver.onNext(transactionResponse);
    }

    @Override
    public void onError(Throwable t) {
        responseObserver.onError(Status.INTERNAL.withDescription(t.getMessage()).asException());
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
