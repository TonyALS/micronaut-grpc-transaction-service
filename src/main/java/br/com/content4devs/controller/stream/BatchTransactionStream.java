package br.com.content4devs.controller.stream;

import br.com.content4devs.service.transaction.ITransactionService;
import br.com.content4devs.v1.transaction.BatchTransactionResponse;
import br.com.content4devs.v1.transaction.TransactionRequest;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.content4devs.functions.TransactionConverter.toTransactionRequestDto;

public class BatchTransactionStream implements StreamObserver<TransactionRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchTransactionStream.class);

    private final StreamObserver<BatchTransactionResponse> responseObserver;

    private final ITransactionService service;

    private int success = 0;

    public BatchTransactionStream(StreamObserver<BatchTransactionResponse> responseObserver, ITransactionService service) {
        this.responseObserver = responseObserver;
        this.service = service;
    }

    @Override
    public void onNext(TransactionRequest value) {
        service.createTransaction(toTransactionRequestDto.apply(value));
        success++;
    }

    @Override
    public void onError(Throwable t) {
        responseObserver.onError(Status.INTERNAL.withDescription(t.getMessage()).asException());
    }

    @Override
    public void onCompleted() {
        responseObserver.onNext(BatchTransactionResponse.newBuilder()
                .setMessage("Transactions processed successfully")
                .setTotalTransactionsCreated(success)
                .build());
        responseObserver.onCompleted();
        LOGGER.info("Stream finished. {} transactions created successfully", success);
    }
}
