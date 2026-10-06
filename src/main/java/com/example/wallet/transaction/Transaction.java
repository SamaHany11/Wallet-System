package com.example.wallet.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Transaction {

    private final String id;
    private final String accountId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final Instant createdAt;
    private final TransactionStatus status;

    public Transaction(String accountId, TransactionType type, BigDecimal amount) {
        this(UUID.randomUUID().toString(), accountId, type, amount, Instant.now(), TransactionStatus.COMPLETED);
    }

    public Transaction(String id, String accountId, TransactionType type, BigDecimal amount,
                       Instant createdAt, TransactionStatus status) {
        this.id = Objects.requireNonNull(id);
        this.accountId = Objects.requireNonNull(accountId);
        this.type = Objects.requireNonNull(type);
        this.amount = Objects.requireNonNull(amount);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.status = Objects.requireNonNull(status);
    }

    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
    public TransactionStatus getStatus() { return status; }
}
