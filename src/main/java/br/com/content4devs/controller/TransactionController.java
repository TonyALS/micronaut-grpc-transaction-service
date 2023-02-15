package br.com.content4devs.controller;


import br.com.content4devs.controller.stream.BatchTransactionStream;
import br.com.content4devs.controller.stream.CreateTransactionBidirectionalStream;
import br.com.content4devs.dto.transaction.TransactionRequestDto;
import br.com.content4devs.dto.transaction.TransactionResponseDto;
import br.com.content4devs.service.transaction.ITransactionService;
import br.com.content4devs.v1.transaction.*;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;

import java.time.LocalDate;

import static br.com.content4devs.functions.DateTimeConverter.stringToLocalDate;
import static br.com.content4devs.functions.TransactionConverter.toTransactionRequestDto;
import static br.com.content4devs.functions.TransactionConverter.toTransactionResponse;


@GrpcService
public class TransactionController extends TransactionServiceGrpc.TransactionServiceImplBase {

    private final ITransactionService service;

    public TransactionController(ITransactionService service) {
        this.service = service;
    }

    @Override
    public void create(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        TransactionRequestDto requestDto = toTransactionRequestDto.apply(request);
        TransactionResponseDto responseDto = service.createTransaction(requestDto);
        responseObserver.onNext(toTransactionResponse.apply(responseDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getById(TransactionId request, StreamObserver<TransactionResponse> responseObserver) {
        TransactionResponseDto responseDto = service.getById(request.getId());
        responseObserver.onNext(toTransactionResponse.apply(responseDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getByPeriod(Period request, StreamObserver<TransactionResponse> responseObserver) {
        LocalDate startDate = stringToLocalDate.apply(request.getStartDate());
        LocalDate endDate = stringToLocalDate.apply(request.getEndDate());
        service.findByPeriod(startDate, endDate)
                .stream().map(toTransactionResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<TransactionRequest> createBatchTransactions(StreamObserver<BatchTransactionResponse> responseObserver) {
        return new BatchTransactionStream(responseObserver, service);
    }

    @Override
    public StreamObserver<TransactionRequest> createTransactionStream(StreamObserver<TransactionResponse> responseObserver) {
        return new CreateTransactionBidirectionalStream(responseObserver, service);
    }
}
