package br.com.content4devs.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDto(
        Long id,
        Long accountId,
        BigDecimal amount,
        TransactionType transactionType,
        LocalDate transactionDate
) {}
