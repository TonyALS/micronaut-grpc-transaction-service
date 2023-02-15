package br.com.content4devs.functions;

import br.com.content4devs.dto.transaction.TransactionRequestDto;
import br.com.content4devs.dto.transaction.TransactionResponseDto;
import br.com.content4devs.dto.transaction.TransactionType;
import br.com.content4devs.exceptions.TransactionTypeException;
import br.com.content4devs.model.Transaction;
import br.com.content4devs.v1.transaction.OperationType;
import br.com.content4devs.v1.transaction.TransactionRequest;
import br.com.content4devs.v1.transaction.TransactionResponse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

import static br.com.content4devs.functions.DateTimeConverter.*;

public class TransactionConverter {
    public static final Function<TransactionRequest, TransactionRequestDto> toTransactionRequestDto =
            req -> {
                if (isInvalidTransactionType(req.getOperationType().name())) {
                    throw new TransactionTypeException("Transaction type must be DEBIT or CREDIT");
                }
                return new TransactionRequestDto(
                        req.getAccountId(),
                        new BigDecimal(req.getAmount()),
                        TransactionType.valueOf(req.getOperationType().name()),
                        stringToLocalDate.apply(req.getTransactionDate())
                );
            };

    public static final Function<TransactionResponseDto, TransactionResponse> toTransactionResponse =
            resDto -> TransactionResponse.newBuilder()
                    .setId(resDto.id())
                    .setAccountId(resDto.accountId())
                    .setAmount(resDto.amount().toString())
                    .setOperationType(OperationType.valueOf(resDto.transactionType().name()))
                    .setTransactionDate(localDateToString.apply(resDto.transactionDate()))
                    .build();

    public static final Function<TransactionRequestDto, Transaction> toTransactionModel =
            dto -> new Transaction(
                    dto.accountId(),
                    dto.amount(),
                    dto.transactionType().name(),
                    dto.transactionDate()
            );

    public static final Function<Transaction, TransactionResponseDto> toTransactionResponseDto =
            transaction -> new TransactionResponseDto(
                    transaction.getId(),
                    transaction.getAccountId(),
                    transaction.getAmount(),
                    TransactionType.valueOf(transaction.getOperationType()),
                    transaction.getTransactionDate()
            );

    public static boolean isInvalidTransactionType(String trxType) {
        return Arrays.stream(TransactionType.values())
                .noneMatch(transactionType -> transactionType.name().equals(trxType));
    }
}
