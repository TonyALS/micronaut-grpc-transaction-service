package br.com.content4devs.service.transaction;

import br.com.content4devs.dto.transaction.TransactionRequestDto;
import br.com.content4devs.dto.transaction.TransactionResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);
    TransactionResponseDto getById(Long id);

    List<TransactionResponseDto> findByPeriod(
            LocalDate start,
            LocalDate end
    );
}
