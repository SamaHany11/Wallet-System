package com.example.wallet.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {

    @Test
    void shouldCreateAccountWithZeroBalance() {
        AccountService service = new AccountService();

        Account account = service.createAccount("A-1", "Alice");

        assertEquals("Alice", account.getOwnerName());
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
    }

    @Test
    void shouldDepositAndWithdraw() {
        Account account = new Account("A-1", "Alice");

        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("30.00"));

        assertEquals(new BigDecimal("70.00"), account.getBalance());
    }
}
