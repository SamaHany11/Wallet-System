package com.example.wallet.transfer;

import com.example.wallet.account.Account;
import com.example.wallet.exception.InvalidAmountException;
import com.example.wallet.transaction.Transaction;
import com.example.wallet.transaction.TransactionService;
import com.example.wallet.transaction.TransactionType;

import java.math.BigDecimal;

public class TransferService {

    private final TransactionService transactionService;

    public TransferService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction transfer(Account source, Account destination, BigDecimal amount) {
        // TODO: implement transfer business rules and transaction recording.
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        source.withdraw(amount);
        destination.deposit(amount);
        transactionService.record(source.getId(), TransactionType.TRANSFER_OUT, amount);
        return transactionService.record(destination.getId(), TransactionType.TRANSFER_IN, amount);
    }
}
