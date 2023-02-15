package br.com.content4devs.service.transaction.impl;

import br.com.content4devs.dto.transaction.TransactionRequestDto;
import br.com.content4devs.dto.transaction.TransactionResponseDto;
import br.com.content4devs.exceptions.TransactionNotFoundException;
import br.com.content4devs.model.Transaction;
import br.com.content4devs.repository.ITransactionRepository;
import br.com.content4devs.service.transaction.ITransactionService;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.content4devs.functions.TransactionConverter.toTransactionModel;
import static br.com.content4devs.functions.TransactionConverter.toTransactionResponseDto;


@Singleton
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository repository;

    public TransactionServiceImpl(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {
        Transaction transaction = toTransactionModel.apply(requestDto);
        Transaction createdTransaction = repository.save(transaction);
        return toTransactionResponseDto.apply(createdTransaction);
    }

    @Override
    public TransactionResponseDto getById(Long id) {
        Optional<Transaction> transaction = repository.findById(id);
        return transaction.map(toTransactionResponseDto)
                .orElseThrow(() -> new TransactionNotFoundException(String.format("Transaction with ID %s not found", id)));
    }

    @Override
    public List<TransactionResponseDto> findByPeriod(LocalDate start, LocalDate end) {
        List<Transaction> transactions = repository.findByTransactionDateBetween(start, end);
        return transactions.stream()
                .map(toTransactionResponseDto)
                .toList();
    }
}
