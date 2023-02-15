package br.com.content4devs.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    private BigDecimal amount;
    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    public Transaction() {
    }

    public Transaction(Long accountId, BigDecimal amount, String operationType, LocalDate transactionDate) {
        this.accountId = accountId;
        this.amount = amount;
        this.operationType = operationType;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getOperationType() {
        return operationType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
