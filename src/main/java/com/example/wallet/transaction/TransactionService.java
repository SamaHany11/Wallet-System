package com.example.wallet.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public Transaction record(String accountId, TransactionType type, BigDecimal amount) {
        // TODO: validate input and prevent duplicate processing where appropriate.
        Transaction transaction = new Transaction(accountId, type, amount);
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> findByAccountId(String accountId) {
        // TODO: avoid leaking mutable internal state and return transactions for the account.
        return Collections.unmodifiableList(
                transactions.stream()
                        .filter(t -> t.getAccountId().equals(accountId))
                        .toList()
        );
    }
}
