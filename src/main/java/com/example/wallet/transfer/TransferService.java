package com.example.wallet.transfer;

import com.example.wallet.account.Account;
import com.example.wallet.exception.InvalidTransferException;
import com.example.wallet.transaction.Transaction;
import com.example.wallet.transaction.TransactionService;
import com.example.wallet.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Objects;

public class TransferService {

    private final TransactionService transactionService;

    public TransferService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction transfer(Account source, Account destination, BigDecimal amount) {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(destination, "destination must not be null");
        if (source.getId().equals(destination.getId())) {
            throw new InvalidTransferException("Cannot transfer to the same account");
        }
        source.ensureCanWithdraw(amount);
        destination.ensureCanDeposit(amount);

        source.withdraw(amount);
        destination.deposit(amount);

        transactionService.record(source.getId(), TransactionType.TRANSFER_OUT, amount);
        return transactionService.record(destination.getId(), TransactionType.TRANSFER_IN, amount);
    }
}