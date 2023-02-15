package br.com.content4devs.repository;

import br.com.content4devs.model.Transaction;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionDateBetween(
            LocalDate start,
            LocalDate end
    );
}
