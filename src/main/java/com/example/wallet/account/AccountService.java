package com.example.wallet.account;

import com.example.wallet.exception.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String id, String ownerName) {
        // TODO: reject duplicate IDs and create the account.
        Account account = new Account(id, ownerName);
        accounts.put(id, account);
        return account;
    }

    public Account getAccount(String id) {
        // TODO
        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + id);
        }
        return account;
    }
}
