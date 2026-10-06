package com.example.wallet.account;

import com.example.wallet.exception.AccountNotFoundException;
import com.example.wallet.exception.DuplicateAccountException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String id, String ownerName) {
        if (accounts.containsKey(id)) {
            throw new DuplicateAccountException("Account already exists: " + id);
        }
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
