package com.example.wallet.account;

import com.example.wallet.exception.InsufficientBalanceException;
import com.example.wallet.exception.InvalidAccountStateException;
import com.example.wallet.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountEdgeCaseTest {

    @Test
    void shouldRejectZeroAndNegativeDeposit() {
        Account account = new Account("A-1", "Alice");

        assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.ZERO));
        assertThrows(InvalidAmountException.class, () -> account.deposit(new BigDecimal("-5")));
    }

    @Test
    void shouldRejectWithdrawMoreThanBalance() {
        Account account = new Account("A-1", "Alice");
        account.deposit(new BigDecimal("100.00"));

        assertThrows(InsufficientBalanceException.class,
                () -> account.withdraw(new BigDecimal("100.01")));
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    void suspendedAccountShouldRejectDepositAndWithdraw() {
        Account account = new Account("A-1", "Alice");
        account.deposit(new BigDecimal("100.00"));
        account.suspend();

        assertThrows(InvalidAccountStateException.class, () -> account.deposit(new BigDecimal("1")));
        assertThrows(InvalidAccountStateException.class, () -> account.withdraw(new BigDecimal("1")));
    }
    @Test
    void shouldNotCloseAccountThatStillHasMoney() {
        Account account = new Account("A-1", "Alice");
        account.deposit(new BigDecimal("10.00"));

        assertThrows(InvalidAccountStateException.class, account::close);
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
    }

    @Test
    void shouldNotCloseTwice() {
        Account account = new Account("A-1", "Alice");
        account.close();

        assertThrows(InvalidAccountStateException.class, account::close);
    }
}