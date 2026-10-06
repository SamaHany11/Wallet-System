package com.example.wallet.account;

import com.example.wallet.exception.InsufficientBalanceException;
import com.example.wallet.exception.InvalidAccountStateException;
import com.example.wallet.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private final String id;
    private final String ownerName;
    private BigDecimal balance;
    private AccountStatus status;

    public Account(String id, String ownerName) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.ownerName = Objects.requireNonNull(ownerName, "ownerName must not be null");
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
    }

    public String getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public BigDecimal getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }

    public void deposit(BigDecimal amount) {
        // TODO: validate the amount and account state, then update the balance.
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        // TODO: update the balance after validation.
        // Intentionally incomplete for the challenge.
    }

    public void withdraw(BigDecimal amount) {
        // TODO: validate account state and ensure sufficient balance.
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        balance = balance.subtract(amount);
    }

    public void suspend() {
        status = AccountStatus.SUSPENDED;
    }

    public void close() {
        // TODO: decide which business rule should apply before closing.
        status = AccountStatus.CLOSED;
    }

    private void ensureActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException("Account is not active");
        }
    }
}
