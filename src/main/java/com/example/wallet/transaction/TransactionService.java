package com.example.wallet.transaction;

import com.example.wallet.exception.DuplicateTransactionException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();
    private final Set<String> recordedIds = new HashSet<>();

    public Transaction record(String accountId, TransactionType type, BigDecimal amount) {
        return record(new Transaction(accountId, type, amount));
    }

    public Transaction record(Transaction transaction) {
        if (!recordedIds.add(transaction.getId())) {
            throw new DuplicateTransactionException("Transaction already recorded: " + transaction.getId());
        }
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> findByAccountId(String accountId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountId().equals(accountId)) {
                result.add(transaction);
            }
        }
        return Collections.unmodifiableList(result);
    }
}