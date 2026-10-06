package com.example.wallet.account;

import com.example.wallet.common.AmountRules;
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
        ensureCanDeposit(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        ensureCanWithdraw(amount);
        balance = balance.subtract(amount);
    }

    public void ensureCanDeposit(BigDecimal amount) {
        AmountRules.requirePositive(amount);
        ensureActive();
    }

    public void ensureCanWithdraw(BigDecimal amount) {
        AmountRules.requirePositive(amount);
        ensureActive();
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }


    public void suspend() {
        if (status == AccountStatus.CLOSED) {
            throw new InvalidAccountStateException("A closed account cannot be suspended");
        }
        status = AccountStatus.SUSPENDED;
    }

    public void close() {
        if (status == AccountStatus.CLOSED) {
            throw new InvalidAccountStateException("Account is already closed");
        }
        if (balance.signum() != 0) {
            throw new InvalidAccountStateException("Withdraw the remaining balance before closing the account");
        }
        status = AccountStatus.CLOSED;
    }

    private void ensureActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException("Account is not active");
        }
    }
}
