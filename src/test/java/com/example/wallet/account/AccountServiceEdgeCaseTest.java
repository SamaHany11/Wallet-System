package com.example.wallet.account;

import com.example.wallet.exception.AccountNotFoundException;
import com.example.wallet.exception.DuplicateAccountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceEdgeCaseTest {

    @Test
    void shouldRejectDuplicateAccountId() {
        AccountService service = new AccountService();
        service.createAccount("A-1", "Alice");

        assertThrows(DuplicateAccountException.class, () -> service.createAccount("A-1", "Bob"));
    }

    @Test
    void shouldFailToFindUnknownAccount() {
        AccountService service = new AccountService();

        assertThrows(AccountNotFoundException.class, () -> service.getAccount("nope"));
    }
}